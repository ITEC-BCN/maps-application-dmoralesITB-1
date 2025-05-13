package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    // Screens for the main navigation flow
    @Serializable
    object Pantalla1 : Destinations()

    @Serializable
    object Pantalla2 : Destinations()

    @Serializable
    object Pantalla3 : Destinations()

    // Screens for the drawer navigation flow
    @Serializable
    object Lista : Destinations()

    @Serializable
    object Settings : Destinations()
}
