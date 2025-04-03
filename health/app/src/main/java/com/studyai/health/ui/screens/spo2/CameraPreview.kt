package com.studyai.health.ui.screens.spo2

import android.content.Context
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
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
import androidx.compose.runtime.remember
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
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val TAG = "CameraPreview"

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
    
    // Remember the camera provider future
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    
    // Create the analyzer for SpO2 calculation
    val analyzer = remember { 
        OxygenSaturationAnalyzer(
            context = context, 
            onSpO2Reading = onSpO2Reading,
            onError = onError
        )
    }
    
    // Remember our camera instance
    var camera = remember { null as Camera? }
    
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
                // Create a PreviewView
                val previewView = PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                
                // Return the preview view
                previewView
            },
            update = { previewView ->
                // Set up the camera when the view is created or updated
                val lifecycleOwnerCurrent = lifecycleOwner
                val contextCurrent = context
                
                // Use this previewView instance
                previewView.apply {
                    // Implementation details remain the same
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                
                // Bind camera outside of Composable context
                cameraProviderFuture.addListener({
                    try {
                        // Get the camera provider
                        val cameraProvider = cameraProviderFuture.get()
                        
                        // Unbind any previous use cases
                        cameraProvider.unbindAll()
                        
                        // Configure camera selector to use back camera
                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        
                        // Set up Preview use case
                        val previewUseCase = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        
                        // Set up Image Analysis use case
                        val imageAnalysisUseCase = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build().also {
                                it.setAnalyzer(analysisExecutor, analyzer)
                            }
                        
                        // Bind use cases to camera
                        camera = cameraProvider.bindToLifecycle(
                            lifecycleOwnerCurrent,
                            cameraSelector,
                            previewUseCase,
                            imageAnalysisUseCase
                        )
                        
                        // Enable torch if available and requested
                        if (enableTorch && camera?.cameraInfo?.hasFlashUnit() == true) {
                            camera?.cameraControl?.enableTorch(true)
                        } else if (enableTorch) {
                            Log.w(TAG, "Torch requested but not available on this device")
                        }
                        
                    } catch (e: Exception) {
                        Log.e(TAG, "Error setting up camera", e)
                        onError("Failed to set up camera: ${e.message}")
                    }
                }, ContextCompat.getMainExecutor(contextCurrent))
            }
        )
    }
    
    // Clean up resources when component is disposed
    DisposableEffect(Unit) {
        onDispose {
            analysisExecutor.shutdown()
            camera?.cameraControl?.enableTorch(false)
        }
    }
}

/**
 * Helper function to get camera provider asynchronously.
 */
private suspend fun getCameraProvider(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    context: Context
): ProcessCameraProvider {
    return suspendCoroutine { continuation ->
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                continuation.resume(cameraProvider)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }, ContextCompat.getMainExecutor(context))
    }
} 