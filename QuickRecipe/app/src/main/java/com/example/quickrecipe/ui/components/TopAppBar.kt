package com.example.quickrecipe.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickRecipeTopAppBar(
    cuisines: List<String>,
    selectedCuisine: String,
    onCuisineSelected: (String) -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text("QuickRecipe") },
        actions = {
            IconButton(onClick = { /* TODO: Profile action */ }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
        },
        navigationIcon = {
            var expanded by remember { mutableStateOf(false) }
            
            Box {
                Row(
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(selectedCuisine)
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select cuisine")
                }
                
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    cuisines.forEach { cuisine ->
                        DropdownMenuItem(
                            text = { Text(cuisine) },
                            onClick = {
                                onCuisineSelected(cuisine)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    var selected by remember { mutableStateOf("Italian") }

    QuickRecipeTopAppBar(
        cuisines = listOf("Italian", "Mexican", "Chinese", "Indian", "Thai"),
        selectedCuisine = selected,
        onCuisineSelected = { selected = it }
    )
}
