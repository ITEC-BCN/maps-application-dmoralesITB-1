package com.example.mapsapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.material3.ExperimentalMaterial3Api
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreen(
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Effect that runs when permission status changes
    LaunchedEffect(key1 = locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            onPermissionGranted()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Location Permission") }) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    locationPermissionState.status.isGranted -> {

                        Text(
                            "Location permission granted. Loading map...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    locationPermissionState.status.shouldShowRationale -> {

                        Text(
                            "This app needs location permission to show the map with your location. Please grant the permission.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                            Text("Request Permission")
                        }
                    }

                    else -> {

                        Text(
                            "Location permission was denied. Please enable it from settings to use the map feature.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            context.startActivity(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    .apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    })
                        }) {
                            Text("Open Settings")
                        }
                    }
                }
            }
        }
    }
}
