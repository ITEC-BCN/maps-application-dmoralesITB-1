import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val ruta: String
) {
    LIST(Icons.Filled.List, "Lista", "pantalla1"),
    DETAILS(Icons.Filled.Info, "Details", "pantalla3")
}
