package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Pantalla1 : Destinations()

    @Serializable
    object Pantalla2 : Destinations()
    @Serializable
    object Pantalla3 : Destinations()

}
