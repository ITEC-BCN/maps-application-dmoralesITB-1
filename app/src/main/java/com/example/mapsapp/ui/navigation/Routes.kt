package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    // NavWrapper entre pantallas
    @Serializable
    object Pantalla1 : Destinations()

    @Serializable
    object Pantalla2 : Destinations()
    @Serializable
    object Pantalla3 : Destinations()

    // nav de rutas entre opciones del drawer

    @Serializable
    object Lista: Destinations()
    @Serializable
    object Settings: Destinations()


}
