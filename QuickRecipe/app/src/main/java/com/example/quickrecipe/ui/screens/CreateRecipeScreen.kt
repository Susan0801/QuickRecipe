package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickrecipe.data.repository.PostRepository
import com.example.quickrecipe.data.repository.UserRepository
import com.example.quickrecipe.model.Difficulty
import com.example.quickrecipe.model.MockRecipe
import com.example.quickrecipe.model.Recipe
import com.example.quickrecipe.model.User
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(
    modifier: Modifier = Modifier,
    onRecipeCreated: (Recipe) -> Unit,
    onBackPressed: () -> Unit,
    currentUser: User = UserRepository.currentUser.value!!
) {
    // Recipe fields
    var title by remember { mutableStateOf("") }
    var cuisineType by remember { mutableStateOf("Mediterranean") }
    var prepTime by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf(Difficulty.MEDIUM) }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    
    // For post creation
    var postTitle by remember { mutableStateOf("") }
    var createPost by remember { mutableStateOf(true) }
    
    // Dropdown states
    var cuisineDropdownExpanded by remember { mutableStateOf(false) }
    var difficultyDropdownExpanded by remember { mutableStateOf(false) }
    
    val cuisines = listOf("Mediterranean", "Asian", "Italian", "Mexican", "Indian", "Breakfast")
    val difficulties = listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD)
    
    val scrollState = rememberScrollState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Recipe") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (validateRecipe(title, prepTime, cookingTime, ingredients, instructions)) {
                                val prepTimeInt = prepTime.toIntOrNull() ?: 0
                                val cookingTimeInt = cookingTime.toIntOrNull() ?: 0
                                
                                val newRecipe = Recipe(
                                    id = 0, // Will be assigned by repository
                                    title = title,
                                    ingredients = ingredients.split(",").map { it.trim() },
                                    instructions = instructions.split("\n").map { it.trim() },
                                    cuisineType = cuisineType,
                                    prepTime = prepTimeInt,
                                    cookingTime = cookingTimeInt,
                                    difficulty = difficulty,
                                    dietaryPrefs = emptyList(), // Could add later
                                    imageUrl = null // Could add photo upload later
                                )
                                
                                // Add the new recipe to MockRecipe
                                MockRecipe.addRecipe(newRecipe)
                                
                                if (createPost) {
                                    val postTitleToUse = if (postTitle.isBlank()) "I made: $title" else postTitle
                                    
                                    // Create post with the new recipe using the current user info
                                    PostRepository.createPostWithNewRecipe(
                                        userId = currentUser.id,
                                        username = currentUser.name,
                                        title = postTitleToUse,
                                        description = description.ifBlank { "I created a new $cuisineType recipe!" },
                                        imageUrl = null, // Could add photo upload later
                                        recipe = newRecipe
                                    )
                                }
                                
                                onRecipeCreated(newRecipe)
                            }
                        },
                        enabled = validateRecipe(title, prepTime, cookingTime, ingredients, instructions)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Recipe"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Recipe Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Title") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Cuisine Type
            ExposedDropdownMenuBox(
                expanded = cuisineDropdownExpanded,
                onExpandedChange = { cuisineDropdownExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = cuisineType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Cuisine Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = cuisineDropdownExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = cuisineDropdownExpanded,
                    onDismissRequest = { cuisineDropdownExpanded = false }
                ) {
                    cuisines.forEach { cuisine ->
                        DropdownMenuItem(
                            text = { Text(cuisine) },
                            onClick = {
                                cuisineType = cuisine
                                cuisineDropdownExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Time and Difficulty Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Prep Time
                OutlinedTextField(
                    value = prepTime,
                    onValueChange = { prepTime = it.filter { char -> char.isDigit() } },
                    label = { Text("Prep Time (mins)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                
                // Cooking Time
                OutlinedTextField(
                    value = cookingTime,
                    onValueChange = { cookingTime = it.filter { char -> char.isDigit() } },
                    label = { Text("Cooking Time (mins)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Difficulty
            ExposedDropdownMenuBox(
                expanded = difficultyDropdownExpanded,
                onExpandedChange = { difficultyDropdownExpanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = difficulty.name.lowercase().capitalize(),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Difficulty") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = difficultyDropdownExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = difficultyDropdownExpanded,
                    onDismissRequest = { difficultyDropdownExpanded = false }
                ) {
                    difficulties.forEach { difficultyOption ->
                        DropdownMenuItem(
                            text = { Text(difficultyOption.name.lowercase().capitalize()) },
                            onClick = {
                                difficulty = difficultyOption
                                difficultyDropdownExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Ingredients
            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients (comma-separated)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Instructions
            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructions (one per line)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Post Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Share as Post",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Switch(
                            checked = createPost,
                            onCheckedChange = { createPost = it }
                        )
                    }
                    
                    if (createPost) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = postTitle,
                            onValueChange = { postTitle = it },
                            label = { Text("Post Title (optional)") },
                            placeholder = { Text("I made: $title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description (optional)") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Photo button - could expand this functionality later
                        OutlinedButton(
                            onClick = { /* Photo upload logic */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.InsertPhoto,
                                contentDescription = "Add Photo"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Add Photo")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Finish Button
            Button(
                onClick = {
                    if (validateRecipe(title, prepTime, cookingTime, ingredients, instructions)) {
                        val prepTimeInt = prepTime.toIntOrNull() ?: 0
                        val cookingTimeInt = cookingTime.toIntOrNull() ?: 0
                        
                        val newRecipe = Recipe(
                            id = 0, // Will be assigned by repository
                            title = title,
                            ingredients = ingredients.split(",").map { it.trim() },
                            instructions = instructions.split("\n").map { it.trim() },
                            cuisineType = cuisineType,
                            prepTime = prepTimeInt,
                            cookingTime = cookingTimeInt,
                            difficulty = difficulty,
                            dietaryPrefs = emptyList(), // Could add later
                            imageUrl = null // Could add photo upload later
                        )
                        
                        // Add the new recipe to MockRecipe
                        MockRecipe.addRecipe(newRecipe)
                        
                        if (createPost) {
                            val postTitleToUse = if (postTitle.isBlank()) "I made: $title" else postTitle
                            
                            // Create post with the new recipe using the current user info
                            PostRepository.createPostWithNewRecipe(
                                userId = currentUser.id,
                                username = currentUser.name,
                                title = postTitleToUse,
                                description = description.ifBlank { "I created a new $cuisineType recipe!" },
                                imageUrl = null, // Could add photo upload later
                                recipe = newRecipe
                            )
                        }
                        
                        onRecipeCreated(newRecipe)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = validateRecipe(title, prepTime, cookingTime, ingredients, instructions)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Finish",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Finish Recipe",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(100.dp)) // Extra space at the bottom
        }
    }
}

private fun validateRecipe(
    title: String,
    prepTime: String,
    cookingTime: String,
    ingredients: String,
    instructions: String
): Boolean {
    return title.isNotBlank() && 
           prepTime.isNotBlank() && 
           cookingTime.isNotBlank() && 
           ingredients.isNotBlank() && 
           instructions.isNotBlank()
}

// Helper extension to capitalize first letter
private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
} 