package com.example.mapsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(val text: String,
                      val icon: ImageVector,
                      val ruta: String) {


    MAPS("Mapa", Icons.Filled.Place, Destinations.Pantalla1::class.simpleName!!),

    LISTA("Lista", Icons.Filled.List, Destinations.Lista::class.simpleName!!),

    SETTINGS("Configuración", Icons.Filled.Settings, Destinations.Settings::class.simpleName!!)
}
