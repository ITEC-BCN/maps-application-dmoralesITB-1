package com.example.mapsapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mapsapp.utils.PermissionStatus
import androidx.compose.runtime.State
import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class PermissionViewModel: ViewModel() {
    // Using StateFlow for permission status
    private val _permissionStatus = MutableStateFlow<PermissionStatus?>(null)
    val permissionStatus: StateFlow<PermissionStatus?> = _permissionStatus.asStateFlow()

    // Function to update permission status
    fun updatePermissionStatus(status: PermissionStatus) {
        _permissionStatus.value = status
    }

    // Function to check permission status
    fun checkPermissionStatus(context: Context): PermissionStatus {
        return if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            PermissionStatus.Granted
        } else {
            PermissionStatus.Denied
        }
    }

    // Function to request permission (This will usually be triggered from the UI)
    fun requestPermission(context: Context, permission: String) {
        viewModelScope.launch {
            // Check if permission is already granted
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                _permissionStatus.value = PermissionStatus.Granted
            } else {
                // Here you would typically launch the permission request using ActivityResultLauncher
                // This part is handled in the composable where you use rememberLauncherForActivityResult
                // After the launcher returns, you update the permission status accordingly
                _permissionStatus.update { PermissionStatus.Denied }
            }
        }
    }
}
