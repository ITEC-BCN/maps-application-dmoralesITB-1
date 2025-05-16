package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    object MyApp : Destination()

    @Serializable
    object Drawer : Destination()

    @Serializable
    object MapScreen : Destination()

    @Serializable
    object MarkerListScreen : Destination()
}