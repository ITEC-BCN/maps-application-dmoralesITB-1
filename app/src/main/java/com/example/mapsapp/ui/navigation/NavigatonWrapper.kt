package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun Navigation(navController : NavHostController){
    NavHost(navController = navController, startDestination = "MapScreen"){
        composable<Pantalla1>{
            MapScreen{navController.navigateTo()}
        }
        composable("CreateMarkerScreen"){
            CreateMarkerScreen()
            }
        composable("DetailMarkerScreen"){
            DetailMarkerScreen()
        }
        composable("PermissionsScreen"){
    }

}