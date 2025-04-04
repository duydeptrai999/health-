package com.studyai.health.ui.screens.spo2

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.studyai.health.R
import com.studyai.health.ui.components.buttons.PrimaryButton
import com.studyai.health.ui.components.cards.StatusCard
import com.studyai.health.ui.components.cards.HealthStatus
import com.studyai.health.ui.components.TopAppBarWithBack
import com.studyai.health.ui.theme.AppIcons
import com.studyai.health.ui.viewmodels.spo2.SpO2ViewModel
import com.studyai.health.ui.viewmodels.spo2.MeasurementState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SpO2MeasurementScreen(
    onBackClick: () -> Unit,
    viewModel: SpO2ViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (cameraPermissionState.status != PermissionStatus.Granted) {
                    cameraPermissionState.launchPermissionRequest()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = cameraPermissionState.status) {
        if (cameraPermissionState.status == PermissionStatus.Granted) {
            viewModel.onCameraPermissionGranted()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.spo2_measurement_title),
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.state) {
                MeasurementState.DISCLAIMER -> DisclaimerContent(
                    onAccept = { viewModel.onDisclaimerAccepted() }
                )
                
                MeasurementState.READY -> ReadyContent(
                    onStartMeasuring = { viewModel.startMeasurement() }
                )
                
                MeasurementState.MEASURING -> MeasuringContent(
                    progress = uiState.measurementProgress,
                    currentReading = uiState.currentReading,
                    onSpO2Reading = { reading, confidence -> 
                        viewModel.onSpO2Reading(reading, confidence)
                    },
                    onError = { message ->
                        viewModel.onMeasurementError(message)
                    }
                )
                
                MeasurementState.RESULT -> ResultContent(
                    spO2Value = uiState.spO2Result,
                    onSaveResult = { viewModel.saveResult() },
                    onMeasureAgain = { viewModel.resetMeasurement() }
                )
                
                MeasurementState.ERROR -> ErrorContent(
                    errorMessage = uiState.errorMessage,
                    onRetry = { viewModel.resetMeasurement() }
                )
            }
            
            if (cameraPermissionState.status != PermissionStatus.Granted) {
                CameraPermissionContent(
                    shouldShowRationale = cameraPermissionState.status.shouldShowRationale,
                    onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
                )
            }
        }
    }
}

@Composable
fun DisclaimerContent(onAccept: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = AppIcons.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.spo2_disclaimer_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(R.string.spo2_disclaimer_content),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryButton(
            text = stringResource(R.string.spo2_disclaimer_accept),
            onClick = onAccept
        )
    }
}

@Composable
fun ReadyContent(onStartMeasuring: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.spo2_instruction_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Camera preview placeholder
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = AppIcons.FavoriteFilled,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.spo2_instruction_content),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryButton(
            text = stringResource(R.string.spo2_start_measurement),
            onClick = onStartMeasuring
        )
    }
}

@Composable
fun MeasuringContent(
    progress: Float,
    currentReading: String,
    onSpO2Reading: (reading: Double, confidence: Double) -> Unit,
    onError: (message: String) -> Unit
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "ProgressAnimation"
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.spo2_measuring_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Hiển thị thông báo trước khi sử dụng camera
        AnimatedVisibility(
            visible = errorMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            errorMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        
        // Use actual CameraPreview instead of placeholder
        CameraPreview(
            enableTorch = true,
            onSpO2Reading = onSpO2Reading,
            onError = { message ->
                errorMessage = message
                onError(message)
            }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Progress indicator
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.size(80.dp),
            strokeWidth = 8.dp,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = currentReading,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Guidance text
        Text(
            text = stringResource(R.string.spo2_measuring_instruction),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        // Thêm hướng dẫn về đèn flash
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Đảm bảo đèn flash sáng và ngón tay che kín cả camera lẫn đèn flash",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ResultContent(
    spO2Value: Int,
    onSaveResult: () -> Unit,
    onMeasureAgain: () -> Unit
) {
    // Determine status based on SpO2 value
    val status = when {
        spO2Value >= 95 -> HealthStatus.EXCELLENT
        spO2Value >= 90 -> HealthStatus.AVERAGE
        else -> HealthStatus.BAD
    }
    
    val description = when {
        spO2Value >= 95 -> stringResource(R.string.spo2_result_normal)
        spO2Value >= 90 -> stringResource(R.string.spo2_result_low)
        else -> stringResource(R.string.spo2_result_danger)
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.spo2_result_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Result value in a circle
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(status.color.copy(alpha = 0.2f))
                .border(3.dp, status.color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$spO2Value",
                    style = MaterialTheme.typography.displayMedium,
                    color = status.color,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "%",
                    style = MaterialTheme.typography.titleLarge,
                    color = status.color
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = status.label,
            style = MaterialTheme.typography.headlineSmall,
            color = status.color,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PrimaryButton(
                text = stringResource(R.string.spo2_save_result),
                onClick = onSaveResult
            )
            
            PrimaryButton(
                text = stringResource(R.string.spo2_measure_again),
                onClick = onMeasureAgain
            )
        }
    }
}

@Composable
fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit
) {
    // Xác định string resource ID dựa trên nội dung thông báo lỗi
    val messageResId = when {
        errorMessage.contains("No valid readings") || 
        errorMessage.contains("Không phát hiện được chỉ số") -> R.string.spo2_error_no_readings
        
        errorMessage.contains("Poor signal") || 
        errorMessage.contains("Tín hiệu yếu") || 
        errorMessage.contains("cover") || 
        errorMessage.contains("che kín") -> R.string.spo2_error_poor_signal
        
        errorMessage.contains("Flash") || 
        errorMessage.contains("flash") || 
        errorMessage.contains("đèn") -> R.string.spo2_error_no_flash
        
        else -> R.string.spo2_error_general
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = AppIcons.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(R.string.spo2_error_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(messageResId),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        PrimaryButton(
            text = stringResource(R.string.retry),
            onClick = onRetry
        )
    }
}

@Composable
fun CameraPermissionContent(
    shouldShowRationale: Boolean,
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = AppIcons.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.permission_camera_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = if (shouldShowRationale) {
                stringResource(R.string.permission_camera_description)
            } else {
                stringResource(R.string.permission_camera_description)
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryButton(
            text = stringResource(R.string.permission_go_to_settings),
            onClick = onRequestPermission
        )
    }
} 