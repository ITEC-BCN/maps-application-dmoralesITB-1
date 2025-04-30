package com.example.mapsapp.ui.navigation

import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val ruta: Destination
) {
}