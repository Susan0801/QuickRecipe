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
import com.example.quickrecipe.data.repository.UserRepository
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickRecipeTopAppBar(
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val currentUser by UserRepository.currentUser
    
    CenterAlignedTopAppBar(
        title = { Text("QuickRecipe") },
        actions = {
            IconButton(onClick = onProfileClick) {
                if (currentUser != null) {
                    // Display a badge with the first letter of user's name
                    BadgedBox(
                        badge = {
                            Badge {
                                Text(
                                    text = "•",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                } else {
                    Icon(Icons.Filled.Person, contentDescription = "Login")
                }
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
        onSettingsClick = {},
        onProfileClick = {}
    )
}
