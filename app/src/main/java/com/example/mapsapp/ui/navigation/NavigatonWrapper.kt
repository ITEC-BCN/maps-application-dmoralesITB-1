package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.screens.DetailMarkerScreen
import com.example.mapsapp.ui.screens.MapsScreen
import com.example.mapsapp.ui.screens.MarkerListScreen

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Pantalla1::class.simpleName!!
    ) {

        composable(Destinations.Pantalla1::class.simpleName!!) {
            MapsScreen(
                modifier = modifier,
                onMapClick = {
                    navController.navigate(Destinations.Pantalla2::class.simpleName!!)
                },
                onMapLongClick = {
                    navController.navigate(Destinations.Pantalla3::class.simpleName!!)
                },
                onMapLoaded = {
                    navController.navigate(Destinations.Pantalla2::class.simpleName!!)
                }
            )
        }

        composable(Destinations.Pantalla2::class.simpleName!!) {
            DetailMarkerScreen {
                navController.navigate(Destinations.Pantalla3::class.simpleName!!)
            }
        }

        composable(Destinations.Pantalla3::class.simpleName!!) {
            MarkerListScreen {
                navController.navigate(Destinations.Pantalla1::class.simpleName!!)
            }
        }
    }
}
