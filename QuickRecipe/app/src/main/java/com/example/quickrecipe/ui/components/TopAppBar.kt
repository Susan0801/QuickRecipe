package com.example.quickrecipe.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickRecipeTopAppBar(
    onSettingsClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text("QuickRecipe") },
        actions = {
            IconButton(onClick = { /* TODO: Profile action */ }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
        },
        navigationIcon = {
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    QuickRecipeTopAppBar(
        onSettingsClick = {}
    )
}
