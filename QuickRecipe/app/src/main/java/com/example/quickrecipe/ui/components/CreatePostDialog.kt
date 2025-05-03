package com.example.quickrecipe.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.quickrecipe.data.repository.PostRepository
import com.example.quickrecipe.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onPostCreated: () -> Unit,
    currentUser: User?
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isCreatingRecipe by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Create New Post",
                    fontSize = 20.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Photo button
                OutlinedButton(
                    onClick = { /* Add photo logic */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.InsertPhoto,
                        contentDescription = "Add photo"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Photo")
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Recipe toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = "Create a new recipe with this post",
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isCreatingRecipe,
                        onCheckedChange = { isCreatingRecipe = it }
                    )
                }

                if (isCreatingRecipe) {
                    Text(
                        text = "You'll be able to add recipe details in the next step",
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            // Create post using the current user information
                            currentUser?.let { user ->
                                PostRepository.createPost(
                                    userId = user.id,
                                    username = user.name,
                                    title = title,
                                    description = description,
                                    imageUrl = null // Could add photo upload later
                                )

                                if (isCreatingRecipe) {
                                    // Navigate to recipe creation with this post info
                                }
                                onPostCreated()
                            }
                        },
                        enabled = title.isNotBlank() && description.isNotBlank() && currentUser != null
                    ) {
                        Text("Post")
                    }
                }
            }
        }
    }
} 