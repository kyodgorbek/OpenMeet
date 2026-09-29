package com.yodgorbek.openmeet.presentation.call

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import org.koin.androidx.compose.koinViewModel

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import io.getstream.video.android.compose.theme.VideoTheme

@Composable
fun CallScreen(callId: String, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val callViewModel: CallViewModel = koinViewModel()
    val isCallActive by callViewModel.isCallActive.collectAsState()
    val error by callViewModel.error.collectAsState()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var hasAudioPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasCameraPermission = permissions[Manifest.permission.CAMERA] ?: hasCameraPermission
        hasAudioPermission = permissions[Manifest.permission.RECORD_AUDIO] ?: hasAudioPermission
        if (hasCameraPermission && hasAudioPermission) {
            callViewModel.joinCall(callId)
        }
    }

    LaunchedEffect(callId) {
        if (hasCameraPermission && hasAudioPermission) {
            callViewModel.joinCall(callId)
        } else {
            launcher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
                )
            )
        }
    }

    VideoTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (!hasCameraPermission || !hasAudioPermission) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Camera and Microphone permissions are required to join the call.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        launcher.launch(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                            )
                        )
                    }) {
                        Text("Grant Permissions")
                    }
                }
            } else if (error != null) {
                Text("Error: $error")
            } else if (isCallActive) {
                val call = io.getstream.video.android.core.StreamVideo.instance().call("default", callId)
                CallContent(
                    call = call,
                    enableInPictureInPicture = true,
                    onBackPressed = {
                        callViewModel.leaveCall()
                        onBackPressed()
                    }
                )
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
