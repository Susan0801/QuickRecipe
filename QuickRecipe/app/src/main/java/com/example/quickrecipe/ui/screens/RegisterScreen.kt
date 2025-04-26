package com.example.quickrecipe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickrecipe.data.repository.UserRepository
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.state.ToggleableState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var bio by rememberSaveable { mutableStateOf("") }
    
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isRegistrationInProgress by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    
    // Focus requesters for fields
    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }
    val bioFocusRequester = remember { FocusRequester() }
    
    // Interaction sources for focus state and press state
    val nameInteractionSource = remember { MutableInteractionSource() }
    val emailInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val confirmPasswordInteractionSource = remember { MutableInteractionSource() }
    val bioInteractionSource = remember { MutableInteractionSource() }
    val registerButtonInteractionSource = remember { MutableInteractionSource() }
    val cancelButtonInteractionSource = remember { MutableInteractionSource() }
    
    // Get pressed states for buttons
    val isRegisterButtonPressed by registerButtonInteractionSource.collectIsPressedAsState()
    val isCancelButtonPressed by cancelButtonInteractionSource.collectIsPressedAsState()
    
    // Validation functions
    val validateName: (String) -> Boolean = { input ->
        if (input.isBlank()) {
            nameError = "Name cannot be empty"
            false
        } else if (input.length < 2) {
            nameError = "Name must be at least 2 characters"
            false
        } else {
            nameError = null
            true
        }
    }
    
    val validateEmail: (String) -> Boolean = { input ->
        if (input.isBlank()) {
            emailError = "Email cannot be empty"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            emailError = "Please enter a valid email address"
            false
        } else {
            emailError = null
            true
        }
    }
    
    val validatePassword: (String) -> Boolean = { input ->
        if (input.isBlank()) {
            passwordError = "Password cannot be empty"
            false
        } else if (input.length < 6) {
            passwordError = "Password must be at least 6 characters"
            false
        } else {
            passwordError = null
            true
        }
    }
    
    val validateConfirmPassword: (String) -> Boolean = { input ->
        if (input != password) {
            confirmPasswordError = "Passwords do not match"
            false
        } else {
            confirmPasswordError = null
            true
        }
    }
    
    // Form validation status
    val isFormValid = name.isNotBlank() && email.isNotBlank() && 
        password.isNotBlank() && confirmPassword.isNotBlank() &&
        nameError == null && emailError == null && 
        passwordError == null && confirmPasswordError == null &&
        password == confirmPassword

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text("Create Account", 
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.Medium
                    ) 
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack, 
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding() // Handle keyboard appearance
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                
                Text(
                    text = "Join QuickRecipe",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Create an account to save your favorite recipes and share your own creations",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Show error message if any
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                // Name Field
                OutlinedTextField(
                    value = name,
                    onValueChange = { 
                        name = it
                        errorMessage = null
                        if (it.isNotBlank()) {
                            validateName(it)
                        } else {
                            nameError = null
                        }
                    },
                    label = { Text("Full Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester)
                        .padding(vertical = 4.dp)
                        .semantics { contentDescription = "Full Name Field" },
                    leadingIcon = { 
                        Icon(Icons.Filled.Person, contentDescription = "Name") 
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { 
                            focusManager.moveFocus(FocusDirection.Down)
                            emailFocusRequester.requestFocus()
                        }
                    ),
                    isError = nameError != null,
                    interactionSource = nameInteractionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                    )
                )
                
                // Show name error
                nameError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        errorMessage = null
                        if (it.isNotBlank()) {
                            validateEmail(it)
                        } else {
                            emailError = null
                        }
                    },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester)
                        .padding(vertical = 4.dp)
                        .semantics { contentDescription = "Email Field" },
                    leadingIcon = { 
                        Icon(Icons.Filled.Email, contentDescription = "Email") 
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { 
                            focusManager.moveFocus(FocusDirection.Down)
                            passwordFocusRequester.requestFocus()
                        }
                    ),
                    isError = emailError != null,
                    interactionSource = emailInteractionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                    )
                )
                
                // Show email error
                emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        errorMessage = null
                        if (it.isNotBlank()) {
                            validatePassword(it)
                            if (confirmPassword.isNotBlank()) {
                                validateConfirmPassword(confirmPassword)
                            }
                        } else {
                            passwordError = null
                        }
                    },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester)
                        .padding(vertical = 4.dp)
                        .semantics { contentDescription = "Password Field" },
                    leadingIcon = { 
                        Icon(Icons.Filled.Lock, contentDescription = "Password") 
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { 
                            focusManager.moveFocus(FocusDirection.Down)
                            confirmPasswordFocusRequester.requestFocus() 
                        }
                    ),
                    isError = passwordError != null,
                    interactionSource = passwordInteractionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                    )
                )
                
                // Show password error
                passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { 
                        confirmPassword = it
                        errorMessage = null
                        if (it.isNotBlank() && password.isNotBlank()) {
                            validateConfirmPassword(it)
                        } else {
                            confirmPasswordError = null
                        }
                    },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(confirmPasswordFocusRequester)
                        .padding(vertical = 4.dp)
                        .semantics { contentDescription = "Confirm Password Field" },
                    leadingIcon = { 
                        Icon(Icons.Filled.Lock, contentDescription = "Confirm Password") 
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                if (isConfirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (isConfirmPasswordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { 
                            focusManager.moveFocus(FocusDirection.Down)
                            bioFocusRequester.requestFocus() 
                        }
                    ),
                    isError = confirmPasswordError != null,
                    interactionSource = confirmPasswordInteractionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                    )
                )
                
                // Show confirm password error
                confirmPasswordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Bio Field (Optional)
                OutlinedTextField(
                    value = bio,
                    onValueChange = { 
                        bio = it
                    },
                    label = { Text("Bio (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .focusRequester(bioFocusRequester)
                        .padding(vertical = 4.dp)
                        .semantics { contentDescription = "Bio Field" },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { 
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    ),
                    interactionSource = bioInteractionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Guidance text when form is not valid
                if (!isFormValid && (name.isNotBlank() || email.isNotBlank() || 
                    password.isNotBlank() || confirmPassword.isNotBlank())) {
                    Text(
                        text = "Please complete all required fields correctly",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                
                // Button Row with Register and Cancel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cancel Button
                    OutlinedButton(
                        onClick = onBackPressed,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = if (isCancelButtonPressed) 
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) 
                            else 
                                Color.Transparent
                        ),
                        interactionSource = cancelButtonInteractionSource
                    ) {
                        Text(
                            "Cancel", 
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Register Button
                    Button(
                        onClick = {
                            // Validate all fields
                            val nameValid = validateName(name)
                            val emailValid = validateEmail(email)
                            val passwordValid = validatePassword(password)
                            val confirmPasswordValid = validateConfirmPassword(confirmPassword)
                            
                            if (nameValid && emailValid && passwordValid && confirmPasswordValid) {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                                isRegistrationInProgress = true
                                
                                // Register with expanded data
                                UserRepository.registerWithProfile(
                                    email = email,
                                    password = password,
                                    name = name,
                                    bio = bio.ifBlank { null },
                                    onSuccess = {
                                        isRegistrationInProgress = false
                                        // Save user data
                                        UserRepository.currentUser.value?.let { user ->
                                            UserRepository.saveUserLogin(context, user)
                                        }
                                        onRegisterSuccess()
                                    },
                                    onError = { error ->
                                        isRegistrationInProgress = false
                                        errorMessage = error
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .weight(1.5f)
                            .height(56.dp),
                        enabled = isFormValid && !isRegistrationInProgress,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        interactionSource = registerButtonInteractionSource
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isRegistrationInProgress) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Register", 
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                
                // Already have an account link
                TextButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Already have an account? Login",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Add bottom spacing for scrolling
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}