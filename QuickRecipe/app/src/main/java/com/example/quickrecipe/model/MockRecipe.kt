package com.example.quickrecipe.model

object MockRecipe {
    val mockRecipes = listOf(
        // Italian Recipes
        Recipe(
            id = 1,
            title = "Margherita Pizza",
            ingredients = listOf("flour", "tomato sauce", "mozzarella cheese", "fresh basil", "olive oil", "salt"),
            instructions = listOf(
                "Make dough with flour, water, yeast, and salt.",
                "Let dough rise for 1 hour.",
                "Flatten dough into a circle.",
                "Spread tomato sauce.",
                "Add sliced mozzarella.",
                "Bake at 450°F for 15 minutes.",
                "Garnish with fresh basil and olive oil."
            ),
            cuisineType = "Italian",
            cookingTime = 20,
            prepTime = 70, // including rise time
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1604068549290-dea0e4a305ca"
        ),
        Recipe(
            id = 2,
            title = "Spaghetti Carbonara",
            ingredients = listOf("spaghetti", "eggs", "pancetta", "parmesan cheese", "black pepper", "salt"),
            instructions = listOf(
                "Cook spaghetti according to package instructions.",
                "Fry pancetta until crispy.",
                "Beat eggs with grated parmesan cheese and pepper.",
                "Drain pasta and immediately add to pancetta pan.",
                "Remove from heat and quickly stir in egg mixture.",
                "Serve immediately with extra parmesan and pepper."
            ),
            cuisineType = "Italian",
            cookingTime = 15,
            prepTime = 10,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1612874742237-6526221588e3"
        ),
        
        // Mexican Recipes
        Recipe(
            id = 3,
            title = "Chicken Tacos",
            ingredients = listOf("chicken breast", "taco seasoning", "tortillas", "lettuce", "tomato", "onion", "sour cream", "cheese"),
            instructions = listOf(
                "Season chicken with taco seasoning.",
                "Cook chicken in a pan until done.",
                "Shred chicken with forks.",
                "Warm tortillas.",
                "Assemble tacos with chicken, lettuce, tomato, onion, cheese, and sour cream."
            ),
            cuisineType = "Mexican",
            cookingTime = 20,
            prepTime = 15,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1565299585323-38d6b0865b47"
        ),
        Recipe(
            id = 4,
            title = "Vegetarian Enchiladas",
            ingredients = listOf("corn tortillas", "black beans", "corn", "bell peppers", "enchilada sauce", "cheese", "cilantro", "avocado"),
            instructions = listOf(
                "Sauté bell peppers until soft.",
                "Mix with black beans and corn.",
                "Fill tortillas with mixture.",
                "Place in baking dish.",
                "Top with enchilada sauce and cheese.",
                "Bake at 375°F for 20 minutes.",
                "Garnish with cilantro and avocado."
            ),
            cuisineType = "Mexican",
            cookingTime = 25,
            prepTime = 15,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1534352956036-cd81e27dd413"
        ),
        
        // Chinese Recipes
        Recipe(
            id = 5,
            title = "Kung Pao Chicken",
            ingredients = listOf("chicken breast", "peanuts", "dried chili peppers", "bell peppers", "green onions", "soy sauce", "rice vinegar", "cornstarch", "sugar", "rice"),
            instructions = listOf(
                "Cut chicken into small cubes and coat with cornstarch.",
                "Fry chicken until golden.",
                "Stir-fry chili peppers, bell peppers, and green onions.",
                "Add chicken back to wok.",
                "Add sauce mixture of soy sauce, vinegar, and sugar.",
                "Mix in peanuts.",
                "Serve over rice."
            ),
            cuisineType = "Chinese",
            cookingTime = 15,
            prepTime = 20,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1525755662778-989d0524087e"
        ),
        Recipe(
            id = 6,
            title = "Vegetable Fried Rice",
            ingredients = listOf("cooked rice", "carrots", "peas", "corn", "eggs", "soy sauce", "sesame oil", "green onions"),
            instructions = listOf(
                "Scramble eggs and set aside.",
                "Stir-fry vegetables until tender.",
                "Add cold cooked rice and break up clumps.",
                "Pour in soy sauce and sesame oil.",
                "Mix in scrambled eggs.",
                "Garnish with chopped green onions."
            ),
            cuisineType = "Chinese",
            cookingTime = 10,
            prepTime = 15,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1603133872878-684f208fb84b"
        ),
        
        // Indian Recipes
        Recipe(
            id = 7,
            title = "Butter Chicken",
            ingredients = listOf("chicken thighs", "yogurt", "garam masala", "butter", "tomato puree", "cream", "onion", "garlic", "ginger", "rice"),
            instructions = listOf(
                "Marinate chicken in yogurt and spices for at least 1 hour.",
                "Cook chicken in a pan until done.",
                "In another pan, sauté onion, garlic, and ginger.",
                "Add tomato puree and spices.",
                "Mix in butter and cream.",
                "Add cooked chicken and simmer for 10 minutes.",
                "Serve with rice."
            ),
            cuisineType = "Indian",
            cookingTime = 30,
            prepTime = 70, // including marination
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db"
        ),
        Recipe(
            id = 8,
            title = "Chana Masala",
            ingredients = listOf("chickpeas", "onion", "tomatoes", "garlic", "ginger", "garam masala", "cumin", "coriander", "turmeric", "rice"),
            instructions = listOf(
                "Sauté onion, garlic, and ginger until golden.",
                "Add spices and cook until fragrant.",
                "Add chopped tomatoes and cook down.",
                "Mix in chickpeas and simmer for 15 minutes.",
                "Garnish with fresh cilantro.",
                "Serve with rice or naan."
            ),
            cuisineType = "Indian",
            cookingTime = 20,
            prepTime = 15,
            dietaryPrefs = listOf("vegan", "vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641"
        ),
        
        // Thai Recipes
        Recipe(
            id = 9,
            title = "Pad Thai",
            ingredients = listOf("rice noodles", "chicken or tofu", "eggs", "bean sprouts", "green onions", "peanuts", "lime", "fish sauce", "tamarind paste", "sugar"),
            instructions = listOf(
                "Soak rice noodles in hot water until soft.",
                "Make sauce with fish sauce, tamarind paste, and sugar.",
                "Stir-fry chicken or tofu until cooked.",
                "Push to one side and scramble eggs.",
                "Add drained noodles and sauce.",
                "Toss in bean sprouts and green onions.",
                "Serve with crushed peanuts and lime wedges."
            ),
            cuisineType = "Thai",
            cookingTime = 15,
            prepTime = 20,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1559314809-0d155014e29e"
        ),
        Recipe(
            id = 10,
            title = "Green Curry",
            ingredients = listOf("chicken or tofu", "coconut milk", "green curry paste", "bamboo shoots", "bell peppers", "Thai basil", "fish sauce", "sugar", "rice"),
            instructions = listOf(
                "Simmer coconut milk and curry paste.",
                "Add chicken or tofu and cook until done.",
                "Mix in vegetables and cook until tender.",
                "Season with fish sauce and sugar.",
                "Stir in Thai basil leaves.",
                "Serve with steamed rice."
            ),
            cuisineType = "Thai",
            cookingTime = 25,
            prepTime = 15,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1455619452474-d2be8b1e70cd"
        )
    )

    // Get all recipes
    fun getAllRecipes(): List<Recipe> {
        return mockRecipes
    }
    
    // Get recipe by ID
    fun getRecipeById(id: Int): Recipe? {
        return mockRecipes.find { it.id == id }
    }

    // Get recipes by cuisine type
    fun getRecipesByCuisine(cuisine: String): List<Recipe> {
        return if (cuisine == "All") {
            mockRecipes
        } else {
            mockRecipes.filter { it.cuisineType.equals(cuisine, ignoreCase = true) }
        }
    }

    // Get recipes by dietary preferences
    fun getRecipesByDietaryPrefs(prefs: List<String>): List<Recipe> {
        return mockRecipes.filter { recipe ->
            prefs.all { pref -> pref in recipe.dietaryPrefs }
        }
    }

    // Get recipes by ingredients
    fun getRecipesByIngredients(ingredients: List<String>): List<Recipe> {
        return mockRecipes.filter { recipe ->
            ingredients.any { ingredient -> 
                recipe.ingredients.any { it.contains(ingredient, ignoreCase = true) }
            }
        }
    }
    
    // Get recipes by maximum cooking time
    fun getRecipesByMaxCookingTime(maxTime: Int): List<Recipe> {
        return mockRecipes.filter { it.cookingTime <= maxTime }
    }
    
    // Get recipes by maximum total time (prep + cooking)
    fun getRecipesByMaxTotalTime(maxTime: Int): List<Recipe> {
        return mockRecipes.filter { (it.prepTime + it.cookingTime) <= maxTime }
    }
    
    // Search recipes by title
    fun searchRecipesByTitle(query: String): List<Recipe> {
        return mockRecipes.filter { 
            it.title.contains(query, ignoreCase = true) 
        }
    }
}