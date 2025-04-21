package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitchenScreen(
    modifier: Modifier = Modifier,
    onFindRecipes: (List<String>) -> Unit = {}
) {
    var ingredients by remember { mutableStateOf(listOf<String>()) }
    var currentIngredient by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "What's in Your Kitchen?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Ingredient Input Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // Input field with add button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = currentIngredient,
                    onValueChange = { 
                        currentIngredient = it
                        showError = false 
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text("Add ingredient") },
                    singleLine = true,
                    isError = showError,
                    supportingText = if (showError) {
                        { Text("Please enter an ingredient") }
                    } else null
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                FilledIconButton(
                    onClick = {
                        if (currentIngredient.isNotBlank() && 
                            !ingredients.contains(currentIngredient.trim())) {
                            ingredients = ingredients + currentIngredient.trim()
                            currentIngredient = ""
                            showError = false
                        } else {
                            showError = currentIngredient.isBlank()
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, "Add ingredient")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredient Chips
            if (ingredients.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(ingredients) { ingredient ->
                        InputChip(
                            selected = false,
                            onClick = { },
                            label = { Text(ingredient) },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        ingredients = ingredients - ingredient
                                    },
                                    modifier = Modifier.size(16.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove $ingredient",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            },
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Find Recipes Button
        Button(
            onClick = { onFindRecipes(ingredients) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = ingredients.isNotEmpty(),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Find Recipes (${ingredients.size})",
                fontSize = 18.sp
            )
        }
    }
}
