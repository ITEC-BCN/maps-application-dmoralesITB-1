package com.example.mapsapp

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.PermissionStatus
import com.example.mapsapp.viewmodels.PermissionViewModel

@Composable
fun MyApp() {
    val activity = LocalContext.current as Activity
    val viewModel = viewModel<PermissionViewModel>()
    val permissionStatus = viewModel.permissionStatus.value
    var alreadyRequested by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        val result = when {
            granted -> PermissionStatus.Granted
            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) -> PermissionStatus.Denied
            else -> PermissionStatus.PermanentlyDenied
        }
        viewModel.updatePermissionStatus(result)
    }

    LaunchedEffect(Unit) {
        if (!alreadyRequested) {
            alreadyRequested = true
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (permissionStatus) {
            null -> Text("Solicitando permiso...")
            PermissionStatus.Granted -> Text("Permiso concedido")
            PermissionStatus.Denied -> {
                Text("Permiso denegado")
                Button(onClick = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
                    Text("Intentar de nuevo")
                }
            }
            PermissionStatus.PermanentlyDenied -> {
                Text("Permiso denegado permanentemente")
            }
        }
    }
}
