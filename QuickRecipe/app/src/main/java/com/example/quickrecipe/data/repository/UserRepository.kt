package com.example.quickrecipe.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.quickrecipe.model.User
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Repository for managing user authentication and user-related data
 */
object UserRepository {
    private const val TAG = "UserRepository"
    private const val PREFS_NAME = "quick_recipe_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_BIO = "user_bio"
    
    // Current logged in user
    val currentUser = mutableStateOf<User?>(null)
    
    // Mock database of users (email -> password)
    private val userCredentials = mutableMapOf<String, String>()
    
    // Mock user data (id -> User)
    private val users = mutableMapOf<String, User>()
    
    // Initialize with some mock users
    init {
        // Add a mock user
        val mockUserId = UUID.randomUUID().toString()
        userCredentials["test@example.com"] = "password123"
        users[mockUserId] = User(
            id = mockUserId,
            email = "test@example.com",
            name = "Test User",
            recipesCreated = 5,
            followersCount = 120,
            followingCount = 45,
            bio = "Passionate food lover and home cook. I enjoy experimenting with flavors and sharing my creations."
        )
    }
    
    /**
     * Initialize the repository with the application context
     */
    fun initialize(context: Context) {
        // Check if a user is already logged in
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userId = prefs.getString(KEY_USER_ID, null)
        val userEmail = prefs.getString(KEY_USER_EMAIL, null)
        val userName = prefs.getString(KEY_USER_NAME, null)
        val userBio = prefs.getString(KEY_USER_BIO, null)
        
        if (userId != null && userEmail != null && userName != null) {
            // Create a user object for the logged in user
            currentUser.value = User(
                id = userId,
                email = userEmail,
                name = userName,
                bio = userBio
            )
            
            Log.d(TAG, "Loaded logged in user: ${currentUser.value?.name}")
        }
    }
    
    /**
     * Attempt to log in a user with the given credentials
     */
    fun login(
        email: String, 
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // In a real app, this would be a network call or database query
        // For the mock implementation, we'll use a delay to simulate network latency
        Thread {
            // Simulate network delay
            Thread.sleep(1000)
            
            // Check if the user exists and password matches
            if (userCredentials.containsKey(email) && userCredentials[email] == password) {
                // Find the user
                val user = users.values.find { it.email == email }
                if (user != null) {
                    // Update current user
                    currentUser.value = user
                    onSuccess()
                } else {
                    onError("User not found")
                }
            } else {
                onError("Invalid email or password")
            }
        }.start()
    }
    
    /**
     * Register a new user
     */
    fun register(
        email: String,
        password: String,
        name: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // In a real app, this would be a network call or database query
        Thread {
            // Simulate network delay
            Thread.sleep(1000)
            
            // Check if the email is already registered
            if (userCredentials.containsKey(email)) {
                onError("Email already registered")
                return@Thread
            }
            
            // Create a new user
            val userId = UUID.randomUUID().toString()
            userCredentials[email] = password
            val newUser = User(
                id = userId,
                email = email,
                name = name
            )
            users[userId] = newUser
            
            // Set as current user
            currentUser.value = newUser
            
            // Success
            onSuccess()
        }.start()
    }
    
    /**
     * Register a new user with additional profile information
     */
    fun registerWithProfile(
        email: String,
        password: String,
        name: String,
        bio: String? = null,
        profileImageUrl: String? = null,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // In a real app, this would be a network call or database query
        Thread {
            // Simulate network delay
            Thread.sleep(1500)
            
            // Check if the email is already registered
            if (userCredentials.containsKey(email)) {
                onError("Email already registered")
                return@Thread
            }
            
            try {
                // Create a new user
                val userId = UUID.randomUUID().toString()
                userCredentials[email] = password
                val newUser = User(
                    id = userId,
                    email = email,
                    name = name,
                    bio = bio,
                    profileImageUrl = profileImageUrl
                )
                users[userId] = newUser
                
                // Set as current user
                currentUser.value = newUser
                
                // Success
                onSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "Error during registration: ${e.message}")
                onError("Registration failed: ${e.message}")
            }
        }.start()
    }
    
    /**
     * Logout the current user
     */
    fun logout(context: Context) {
        // Clear the current user
        currentUser.value = null
        
        // Clear shared preferences
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            remove(KEY_USER_ID)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_NAME)
            remove(KEY_USER_BIO)
            apply()
        }
    }
    
    /**
     * Save the current user's login information
     */
    fun saveUserLogin(context: Context, user: User) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_USER_ID, user.id)
            putString(KEY_USER_EMAIL, user.email)
            putString(KEY_USER_NAME, user.name)
            user.bio?.let { putString(KEY_USER_BIO, it) }
            apply()
        }
    }
    
    /**
     * Update user profile information
     */
    fun updateUserProfile(
        name: String,
        bio: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Get the current user
        val user = currentUser.value ?: run {
            onError("No user logged in")
            return
        }
        
        // Update the user object
        val updatedUser = user.copy(
            name = name,
            bio = bio
        )
        
        // Update in the mock database
        users[user.id] = updatedUser
        
        // Update current user
        currentUser.value = updatedUser
        
        // Success
        onSuccess()
    }
    
    /**
     * Get user by ID
     */
    fun getUserById(userId: String): User? {
        return users[userId]
    }
    
    /**
     * Get all registered users (for testing/demo purposes)
     */
    fun getAllUsers(): List<User> {
        return users.values.toList()
    }
    
    /**
     * Check if an email is already registered
     */
    fun isEmailRegistered(email: String): Boolean {
        return userCredentials.containsKey(email)
    }
    
    fun addCreatedRecipe(recipeId: Int) {
        currentUser.value = currentUser.value?.let { user ->
            user.copy(
                createdRecipeIds = user.createdRecipeIds + recipeId,
                recipesCreated = (user.recipesCreated ?: 0) + 1
            )
        }
    }
}