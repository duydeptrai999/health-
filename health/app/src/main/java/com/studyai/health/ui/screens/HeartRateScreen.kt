package com.studyai.health.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.studyai.health.R
import com.studyai.health.ui.viewmodels.HeartRateViewModel

/**
 * Heart rate measurement screen
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HeartRateScreen(
    navController: NavController,
    viewModel: HeartRateViewModel = viewModel()
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    
    val measurementState by viewModel.measurementState.collectAsState()
    val heartRate by viewModel.heartRate.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    
    // Request camera permission if not granted
    LaunchedEffect(key1 = true) {
        if (cameraPermissionState.status != PermissionStatus.Granted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.heart_rate_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Check camera permission
            if (cameraPermissionState.status == PermissionStatus.Granted) {
                HeartRateMeasurementContent(
                    measurementState = measurementState,
                    heartRate = heartRate,
                    elapsedTime = elapsedTime,
                    onStartMeasurement = { viewModel.startMeasurement(context) },
                    onStopMeasurement = { viewModel.stopMeasurement() },
                    onSaveResult = { viewModel.saveResult() }
                )
            } else {
                CameraPermissionRequest(cameraPermissionState)
            }
        }
    }
}

@Composable
fun HeartRateMeasurementContent(
    measurementState: HeartRateViewModel.MeasurementState,
    heartRate: Int,
    elapsedTime: Int,
    onStartMeasurement: () -> Unit,
    onStopMeasurement: () -> Unit,
    onSaveResult: () -> Unit
) {
    when (measurementState) {
        HeartRateViewModel.MeasurementState.IDLE -> {
            // Instructions
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_rate),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(120.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = stringResource(id = R.string.heart_rate_instruction),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onStartMeasurement,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Bắt đầu đo")
            }
        }
        HeartRateViewModel.MeasurementState.MEASURING -> {
            // Show preview and progress
            // Note: In a real app, we would show the camera preview here
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = stringResource(id = R.string.heart_rate_measuring),
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Thời gian: $elapsedTime giây",
                style = MaterialTheme.typography.bodyMedium
            )
            
            if (heartRate > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "$heartRate BPM",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "(Đang đo...)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onStopMeasurement,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Dừng")
            }
        }
        HeartRateViewModel.MeasurementState.COMPLETED -> {
            // Show results
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_rate),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "$heartRate",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "BPM",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Classification of heart rate
            val (message, color) = when {
                heartRate < 60 -> Pair(
                    stringResource(id = R.string.heart_rate_low),
                    MaterialTheme.colorScheme.tertiary
                )
                heartRate in 60..100 -> Pair(
                    stringResource(id = R.string.heart_rate_normal),
                    MaterialTheme.colorScheme.primary
                )
                else -> Pair(
                    stringResource(id = R.string.heart_rate_high),
                    MaterialTheme.colorScheme.error
                )
            }
            
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onStartMeasurement,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(text = stringResource(id = R.string.heart_rate_retry))
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Button(
                    onClick = onSaveResult,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(text = stringResource(id = R.string.heart_rate_save_result))
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionRequest(permissionState: PermissionState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(id = R.string.permission_camera_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(id = R.string.permission_camera_description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { permissionState.launchPermissionRequest() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Cấp quyền cho camera")
        }
    }
} 