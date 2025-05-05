package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.DrawerItem
import com.example.mapsapp.ui.navigation.Navigation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun MapsScreen(modifier: Modifier = Modifier, navigateTo: () -> Unit) {
    Column(modifier.fillMaxSize()) {
        val itb = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }
        GoogleMap(
            modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawerMenu() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItemIndex = remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                DrawerItem.entries.forEachIndexed { index, drawerItem ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = drawerItem.icon,
                                contentDescription = drawerItem.text
                            )
                        },
                        label = { Text(text = drawerItem.text) },
                        selected = index == selectedItemIndex.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(drawerItem.ruta)
                        }
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Awesome App") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Navigation(navController, Modifier.padding(innerPadding))
        }
    }
}