package com.example.mapsapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Navigation
import com.example.mapsapp.ui.screens.MyDrawerMenu
import com.example.mapsapp.utils.PermissionStatus
import com.example.mapsapp.viewmodels.PermissionViewModel
import android.util.Log

@Composable
fun MyApp() {
    val activity = LocalContext.current as Activity
    val viewModel: PermissionViewModel = viewModel()
    val permissionStatus by viewModel.permissionStatus
    var showRationaleDialog by remember { mutableStateOf(false) }
    var showPermanentlyDeniedDialog by remember { mutableStateOf(false) }
    var alreadyRequested by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        val result = when {
            granted -> {
                Log.d("MyApp", "Permission Granted") // Add this log
                PermissionStatus.Granted
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> PermissionStatus.Denied
            else -> PermissionStatus.PermanentlyDenied
        }
        viewModel.updatePermissionStatus(result)
    }

    LaunchedEffect(key1 = Unit) {
        if (!alreadyRequested) {
            alreadyRequested = true

            val currentPermissionStatus = when {
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED -> PermissionStatus.Granted

                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> PermissionStatus.Denied

                else -> PermissionStatus.PermanentlyDenied
            }

            when (currentPermissionStatus) {
                PermissionStatus.Granted -> {
                    Log.d("MyApp", "Permission already granted")
                    viewModel.updatePermissionStatus(PermissionStatus.Granted)
                }
                PermissionStatus.Denied -> showRationaleDialog = true
                PermissionStatus.PermanentlyDenied -> showPermanentlyDeniedDialog = true
                else -> {
                    Log.d("MyApp", "Launching permission request")
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) // Initial request
                }
            }

        }
    }

    when (permissionStatus) {
        PermissionStatus.Granted -> {
            Log.d("MyApp", "PermissionStatus.Granted - Showing MyDrawerMenu")
            MyDrawerMenu()
        }
        PermissionStatus.Denied -> {
            if (showRationaleDialog) {
                RationaleDialog(
                    onConfirm = {
                        showRationaleDialog = false
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                    onDismiss = {
                        showRationaleDialog = false
                    }
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Permiso denegado")
                    Button(onClick = {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                activity,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            showRationaleDialog = true
                        } else {
                            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }) {
                        Text("Intentar de nuevo")
                    }
                }
            }
        }
        PermissionStatus.PermanentlyDenied -> {
            if (showPermanentlyDeniedDialog.not()) {
                PermanentlyDeniedDialog(
                    onConfirm = {
                        showPermanentlyDeniedDialog = true
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", activity.packageName, null)
                        }
                        activity.startActivity(intent)
                    },
                    onDismiss = {
                        showPermanentlyDeniedDialog = true
                    }
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Permiso denegado permanentemente. Por favor, actívalo en la configuración.")
                }
            }
        }
        null -> {
            Log.d("MyApp", "PermissionStatus is null - Requesting Permissions")
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Requesting Permissions")
            }
        }
    }
}

@Composable
fun RationaleDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Se requiere permiso de ubicación") },
        text = { Text("Necesitamos permiso de ubicación para mostrarte los mapas correctamente.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun PermanentlyDeniedDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Permiso de ubicación denegado permanentemente") },
        text = { Text("Por favor, habilita el permiso de ubicación en la configuración de la aplicación.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Ir a la configuración")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}