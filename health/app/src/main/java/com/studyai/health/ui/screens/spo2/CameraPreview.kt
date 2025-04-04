package com.studyai.health.ui.screens.spo2

import android.content.Context
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.os.Handler
import android.os.Looper
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "CameraPreview"

// Các thông báo lỗi cố định thay vì dùng R.string
private const val ERROR_FLASH_NOT_AVAILABLE = "Đèn flash không khả dụng hoặc không hoạt động. Kết quả đo có thể kém chính xác."
private const val ERROR_CAMERA_INIT_FAILED = "Không thể khởi tạo camera. Đang thử lại..."
private const val ERROR_CAMERA_RETRY_FAILED = "Không thể khởi tạo camera sau nhiều lần thử. Vui lòng khởi động lại ứng dụng."

/**
 * A composable that provides a camera preview for SpO2 measurement.
 * 
 * @param modifier Modifier for styling
 * @param enableTorch Whether to enable the torch/flashlight
 * @param onSpO2Reading Callback for SpO2 reading updates
 * @param onError Callback for error handling
 */
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    enableTorch: Boolean = true,
    onSpO2Reading: (reading: Double, confidence: Double) -> Unit,
    onError: (message: String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // Camera state tracking
    var camera by remember { mutableStateOf<Camera?>(null) }
    var hasFlash by remember { mutableStateOf(false) }
    var torchEnabled by remember { mutableStateOf(false) }
    var cameraInitialized by remember { mutableStateOf(false) }
    var flashAttempts by remember { mutableStateOf(0) }
    var noFlashMode by remember { mutableStateOf(false) }
    var retryCount by remember { mutableStateOf(0) }
    val handler = remember { Handler(Looper.getMainLooper()) }
    
    // Create the analyzer for SpO2 calculation
    val analyzer = remember { 
        OxygenSaturationAnalyzer(
            context = context, 
            onSpO2Reading = onSpO2Reading,
            onError = onError
        )
    }
    
    // Create analysis thread
    val analysisExecutor = remember { Executors.newSingleThreadExecutor() }
    
    // Create a circular preview
    Box(
        modifier = modifier
            .size(300.dp)
            .clip(CircleShape)
            .border(4.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Android View used for camera preview
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                Log.d(TAG, "Creating PreviewView")
                // Create a PreviewView with simplified settings
                val previewView = PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE // Use COMPATIBLE instead of PERFORMANCE
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                
                // Return the preview view
                previewView
            },
            update = { previewView ->
                // Set up the camera when the view is created or updated
                if (!cameraInitialized) {
                    Log.d(TAG, "Setting up camera")
                    
                    // Get camera provider
                    ProcessCameraProvider.getInstance(context).addListener({
                        try {
                            // Setup camera
                            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
                            
                            // Unbind any previous use cases and clean up
                            cameraProvider.unbindAll()
                            
                            // Configure camera selector
                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            
                            // Setup preview
                            val preview = Preview.Builder().build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                            
                            // Setup image analysis with simplified settings
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                            
                            imageAnalysis.setAnalyzer(analysisExecutor, analyzer)
                            
                            try {
                                // Bind use cases
                                camera = cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageAnalysis
                                )
                                
                                // Check if flash is available
                                hasFlash = camera?.cameraInfo?.hasFlashUnit() ?: false
                                Log.d(TAG, "Camera flash available: $hasFlash")
                                
                                // Try to enable flash if available
                                if (hasFlash && enableTorch) {
                                    // Delay to ensure camera is ready before enabling torch
                                    handler.postDelayed({
                                        try {
                                            camera?.cameraControl?.enableTorch(true)?.addListener({
                                                torchEnabled = true
                                                Log.d(TAG, "Torch enabled successfully")
                                            }, ContextCompat.getMainExecutor(context))
                                        } catch (e: Exception) {
                                            Log.e(TAG, "Error enabling torch", e)
                                            flashAttempts++
                                            if (flashAttempts >= 3) {
                                                // Switch to no-flash mode if repeatedly failed
                                                noFlashMode = true
                                                onError(ERROR_FLASH_NOT_AVAILABLE)
                                            }
                                        }
                                    }, 500) // Add a delay before enabling torch
                                }
                                
                                cameraInitialized = true
                                
                            } catch (e: Exception) {
                                Log.e(TAG, "Error binding camera use cases", e)
                                
                                // Retry with progressively longer delays (exponential backoff)
                                retryCount++
                                if (retryCount <= 3) {
                                    onError(ERROR_CAMERA_INIT_FAILED)
                                    handler.postDelayed({
                                        cameraInitialized = false // Force retry
                                    }, retryCount * 1000L) // Increasing delay for each retry
                                } else {
                                    // Give up after 3 retries
                                    onError(ERROR_CAMERA_RETRY_FAILED)
                                    noFlashMode = true
                                }
                            }
                            
                        } catch (e: Exception) {
                            Log.e(TAG, "Error setting up camera", e)
                            retryCount++
                            if (retryCount <= 3) {
                                onError(ERROR_CAMERA_INIT_FAILED)
                                handler.postDelayed({
                                    cameraInitialized = false // Force retry
                                }, retryCount * 1000L)
                            } else {
                                onError(ERROR_CAMERA_RETRY_FAILED)
                                noFlashMode = true
                            }
                        }
                    }, ContextCompat.getMainExecutor(context))
                }
            }
        )
    }
    
    // Notify analyzer about flash status
    LaunchedEffect(noFlashMode) {
        analyzer.setFlashMode(!noFlashMode)
    }
    
    // Try one more time to enable flash after camera is initialized
    LaunchedEffect(cameraInitialized) {
        if (cameraInitialized && hasFlash && !torchEnabled && !noFlashMode) {
            try {
                delay(1000) // Delay to ensure camera is fully stable
                camera?.cameraControl?.enableTorch(enableTorch)?.addListener({
                    torchEnabled = true
                    Log.d(TAG, "Torch enabled from LaunchedEffect after camera init")
                }, ContextCompat.getMainExecutor(context))
            } catch (e: Exception) {
                Log.e(TAG, "Error enabling torch after camera init", e)
                flashAttempts++
                if (flashAttempts >= 3) {
                    noFlashMode = true
                    onError(ERROR_FLASH_NOT_AVAILABLE)
                }
            }
        }
    }
    
    // Clean up resources when component is disposed
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Cleaning up camera resources")
            try {
                camera?.cameraControl?.enableTorch(false)
                // The ProcessCameraProvider will be automatically closed when the Lifecycle stops
                analysisExecutor.shutdown()
            } catch (e: Exception) {
                Log.e(TAG, "Error during cleanup", e)
            }
        }
    }
} 