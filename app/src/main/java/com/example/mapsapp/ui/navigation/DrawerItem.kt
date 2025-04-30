package com.example.mapsapp.ui.navigation

import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val ruta: Destination
) {
    LISTA(Icons.Default.Place, "List", Destination.Lista),
    SETTINGS(Icons.Default.Settings, "Settings", Destination),
}