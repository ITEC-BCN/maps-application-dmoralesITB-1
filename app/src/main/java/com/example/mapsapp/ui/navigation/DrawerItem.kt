package com.example.mapsapp.ui.navigation

import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val ruta: Destination
) {
    LIST(Icons.Filled.List, "Lista", Destination.LIST)
}