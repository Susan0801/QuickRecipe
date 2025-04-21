package com.example.quickrecipe.data.repository

import com.example.quickrecipe.model.Post
import com.example.quickrecipe.model.Recipe
import com.example.quickrecipe.model.Difficulty
import com.example.quickrecipe.model.MockRecipe
import java.util.Date
import java.util.concurrent.atomic.AtomicInteger

/**
 * Repository for managing user posts
 */
object PostRepository {
    private val postIdCounter = AtomicInteger(1)
    private val mockPosts = mutableListOf<Post>()
    
    init {
        // Add some sample posts for demonstration
        addSamplePosts()
    }
    
    private fun addSamplePosts() {
        // Sample posts with existing recipes
        val post1 = Post(
            id = postIdCounter.getAndIncrement(),
            userId = "user1",
            username = "Jamie Oliver",
            title = "Made this amazing pizza today!",
            description = "I tried the Margherita Pizza recipe and added some fresh oregano from my garden. It turned out amazing!",
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591",
            timestamp = Date(),
            likes = 42,
            comments = 7,
            recipeId = 1,
            isNewRecipe = false,
            tags = listOf("pizza", "homemade", "italian")
        )
        
        val post2 = Post(
            id = postIdCounter.getAndIncrement(),
            userId = "user2",
            username = "Gordon Ramsay",
            title = "Quick breakfast idea",
            description = "Made these delicious pancakes in just 15 minutes. Perfect for busy mornings!",
            imageUrl = "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445",
            timestamp = Date(),
            likes = 28,
            comments = 5,
            recipeId = 11,
            isNewRecipe = false,
            tags = listOf("breakfast", "quick", "pancakes")
        )
        
        val post3 = Post(
            id = postIdCounter.getAndIncrement(),
            userId = "user3",
            username = "Nigella Lawson",
            title = "My own spin on Bibimbap",
            description = "I created my own version of Korean Bibimbap with extra vegetables and a spicier sauce. Would love your feedback!",
            imageUrl = "https://images.unsplash.com/photo-1553163147-622ab57be1c7",
            timestamp = Date(),
            likes = 56,
            comments = 12,
            recipeId = 16,
            isNewRecipe = true,
            tags = listOf("korean", "spicy", "vegetable", "original")
        )
        
        mockPosts.addAll(listOf(post1, post2, post3))
    }
    
    fun getAllPosts(): List<Post> {
        return mockPosts.sortedByDescending { it.timestamp }
    }
    
    fun addPost(post: Post): Post {
        val newPost = post.copy(id = postIdCounter.getAndIncrement())
        mockPosts.add(newPost)
        return newPost
    }
    
    fun getPostById(id: Int): Post? {
        return mockPosts.find { it.id == id }
    }
    
    fun getPostsByUser(userId: String): List<Post> {
        return mockPosts.filter { it.userId == userId }
    }
    
    fun addLike(postId: Int) {
        val post = getPostById(postId) ?: return
        val updatedPost = post.copy(likes = post.likes + 1)
        mockPosts.remove(post)
        mockPosts.add(updatedPost)
    }
    
    fun addComment(postId: Int) {
        val post = getPostById(postId) ?: return
        val updatedPost = post.copy(comments = post.comments + 1)
        mockPosts.remove(post)
        mockPosts.add(updatedPost)
    }
    
    fun createPostWithNewRecipe(
        userId: String,
        username: String,
        title: String,
        description: String,
        imageUrl: String?,
        recipe: Recipe
    ): Post {
        // First, add the new recipe to MockRecipe
        val newRecipe = MockRecipe.addRecipe(recipe)
        
        // Then create a post for this recipe
        val post = Post(
            id = postIdCounter.getAndIncrement(),
            userId = userId,
            username = username,
            title = title,
            description = description,
            imageUrl = imageUrl,
            timestamp = Date(),
            recipeId = newRecipe.id,
            isNewRecipe = true,
            tags = listOf(recipe.cuisineType.lowercase())
        )
        
        mockPosts.add(post)
        return post
    }
} 