package com.example.mapsapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable

fun MarkerScreen(navigateTo: (String) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "markerList") {
        composable("markerList") {
            MarkerListScreen(navigateTo)
        }
        composable("detailMarker") {
            DetailMarkerScreen(navigateTo as () -> Unit)
        }
    }
}