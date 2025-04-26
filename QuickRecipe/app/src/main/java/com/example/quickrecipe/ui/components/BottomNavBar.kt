package com.example.quickrecipe.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.quickrecipe.data.entity.NavigationItem
import com.example.quickrecipe.data.repository.FavoritesRepository

/**
 * Reusable bottom navigation bar component for the app
 * 
 * @param currentIndex The currently selected tab index
 * @param onTabSelected Callback for when a tab is selected
 */
@Composable
fun QuickRecipeBottomNavBar(
    currentIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val navigationItems = listOf(
        NavigationItem("Kitchen", Icons.Filled.Home),
        NavigationItem("Recipe", Icons.Filled.Menu),
        NavigationItem("Create", Icons.Filled.Add),
        NavigationItem("Posts", Icons.Filled.Create),
        NavigationItem("Favorite", Icons.Filled.Favorite)
    )
    
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    if (index == 4 && FavoritesRepository.getFavoritesCount() > 0) {
                        // Show badge for favorites
                        BadgedBox(
                            badge = {
                                if (FavoritesRepository.getFavoritesCount() > 0) {
                                    Badge {
                                        Text(FavoritesRepository.getFavoritesCount().toString())
                                    }
                                }
                            }
                        ) {
                            Icon(item.icon, contentDescription = item.title)
                        }
                    } else {
                        Icon(item.icon, contentDescription = item.title)
                    }
                },
                label = { Text(item.title) },
                selected = currentIndex == index,
                onClick = { 
                    onTabSelected(index)
                }
            )
        }
    }
} 