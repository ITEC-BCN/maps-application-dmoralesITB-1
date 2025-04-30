package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.navigation.Destinations.Pantalla1
import com.example.mapsapp.ui.navigation.Destinations.Pantalla2
import com.example.mapsapp.ui.screens.MapsScreen


@Composable
fun Navigation(navController : NavHostController) {
    NavHost(navController, Pantalla1) {
        composable<Pantalla1> {
            MapsScreen { navController.navigate(Pantalla2) }
        }
    }
}


