package com.example.quickrecipe.model

object MockRecipe {
    private val mockRecipes = mutableListOf(
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
            difficulty = Difficulty.MEDIUM,
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
            difficulty = Difficulty.MEDIUM,
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
            difficulty = Difficulty.EASY,
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
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1534352956036-cd81e27dd413"
        ),
        
        // Asian Recipes (formerly Chinese)
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
            cuisineType = "Asian",
            cookingTime = 15,
            prepTime = 20,
            difficulty = Difficulty.MEDIUM,
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
            cuisineType = "Asian",
            cookingTime = 10,
            prepTime = 15,
            difficulty = Difficulty.EASY,
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
            difficulty = Difficulty.HARD,
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
            difficulty = Difficulty.MEDIUM,
            dietaryPrefs = listOf("vegan", "vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641"
        ),
        
        // Mediterranean Recipes (replacing Thai)
        Recipe(
            id = 9,
            title = "Greek Salad",
            ingredients = listOf("cucumber", "tomatoes", "red onion", "bell peppers", "kalamata olives", "feta cheese", "olive oil", "oregano"),
            instructions = listOf(
                "Chop cucumber, tomatoes, onion, and peppers into chunks.",
                "Add kalamata olives.",
                "Crumble feta cheese over top.",
                "Drizzle with olive oil.",
                "Sprinkle with oregano.",
                "Toss gently and serve."
            ),
            cuisineType = "Mediterranean",
            cookingTime = 0,
            prepTime = 15,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1599021456807-4398b7e14d26"
        ),
        Recipe(
            id = 10,
            title = "Shakshuka",
            ingredients = listOf("eggs", "tomatoes", "onion", "bell peppers", "garlic", "cumin", "paprika", "bread"),
            instructions = listOf(
                "Sauté onions and peppers until soft.",
                "Add garlic and spices.",
                "Pour in crushed tomatoes and simmer.",
                "Make wells and crack eggs into them.",
                "Cover and cook until eggs are set.",
                "Serve with crusty bread."
            ),
            cuisineType = "Mediterranean",
            cookingTime = 20,
            prepTime = 10,
            difficulty = Difficulty.MEDIUM,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1590412200988-a436970781fa"
        ),
        
        // Breakfast Recipes
        Recipe(
            id = 11,
            title = "Classic Pancakes",
            ingredients = listOf("flour", "milk", "eggs", "butter", "sugar", "baking powder", "salt", "maple syrup"),
            instructions = listOf(
                "Mix dry ingredients in a bowl.",
                "Whisk wet ingredients in another bowl.",
                "Combine wet and dry ingredients.",
                "Heat griddle or pan.",
                "Pour batter and cook until bubbles form.",
                "Flip and cook other side.",
                "Serve with maple syrup."
            ),
            cuisineType = "Breakfast",
            cookingTime = 15,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1528207776546-365bb710ee93"
        ),
        Recipe(
            id = 12,
            title = "Avocado Toast",
            ingredients = listOf("bread", "avocado", "eggs", "salt", "pepper", "red pepper flakes", "olive oil"),
            instructions = listOf(
                "Toast bread until golden.",
                "Mash avocado with salt and pepper.",
                "Spread on toast.",
                "Top with poached egg if desired.",
                "Sprinkle with red pepper flakes.",
                "Drizzle with olive oil."
            ),
            cuisineType = "Breakfast",
            cookingTime = 5,
            prepTime = 5,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1603046891744-94e55b11b2e1"
        ),

        // More Mediterranean Recipes
        Recipe(
            id = 13,
            title = "Hummus with Pita",
            ingredients = listOf("chickpeas", "tahini", "lemon juice", "garlic", "olive oil", "cumin", "salt", "pita bread"),
            instructions = listOf(
                "Drain and rinse chickpeas.",
                "Blend chickpeas, tahini, lemon juice, and garlic.",
                "Add olive oil while blending.",
                "Season with cumin and salt.",
                "Serve with warm pita bread."
            ),
            cuisineType = "Mediterranean",
            cookingTime = 5,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegan", "vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1577805947697-89e18249d767"
        ),
        Recipe(
            id = 14,
            title = "Moussaka",
            ingredients = listOf("eggplant", "ground lamb", "onion", "tomatoes", "bechamel sauce", "cinnamon", "nutmeg", "potatoes"),
            instructions = listOf(
                "Slice and salt eggplant, let drain.",
                "Make meat sauce with lamb, onions, and tomatoes.",
                "Layer potatoes, eggplant, and meat sauce.",
                "Top with bechamel sauce.",
                "Bake until golden brown."
            ),
            cuisineType = "Mediterranean",
            cookingTime = 45,
            prepTime = 30,
            difficulty = Difficulty.HARD,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1599021419847-d8a7a6aba5b3"
        ),

        // More Asian Recipes
        Recipe(
            id = 15,
            title = "Sushi Roll",
            ingredients = listOf("sushi rice", "nori", "cucumber", "avocado", "salmon", "rice vinegar", "wasabi", "soy sauce"),
            instructions = listOf(
                "Cook and season sushi rice.",
                "Place nori on bamboo mat.",
                "Spread rice on nori.",
                "Add fillings and roll tightly.",
                "Cut into pieces and serve."
            ),
            cuisineType = "Asian",
            cookingTime = 30,
            prepTime = 45,
            difficulty = Difficulty.HARD,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c"
        ),
        Recipe(
            id = 16,
            title = "Korean Bibimbap",
            ingredients = listOf("rice", "spinach", "carrots", "mushrooms", "beef", "egg", "gochujang", "sesame oil"),
            instructions = listOf(
                "Cook rice and prepare vegetables.",
                "Sauté each vegetable separately.",
                "Cook marinated beef.",
                "Fry egg sunny-side up.",
                "Assemble in bowl and mix with gochujang."
            ),
            cuisineType = "Asian",
            cookingTime = 25,
            prepTime = 20,
            difficulty = Difficulty.MEDIUM,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1553163147-622ab57be1c7"
        ),

        // More Indian Recipes
        Recipe(
            id = 17,
            title = "Vegetable Biryani",
            ingredients = listOf("basmati rice", "mixed vegetables", "biryani masala", "saffron", "onions", "yogurt", "mint", "ghee"),
            instructions = listOf(
                "Cook rice with whole spices.",
                "Prepare vegetable masala gravy.",
                "Layer rice and vegetables.",
                "Add saffron milk and mint.",
                "Steam until fragrant."
            ),
            cuisineType = "Indian",
            cookingTime = 40,
            prepTime = 30,
            difficulty = Difficulty.HARD,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8"
        ),
        Recipe(
            id = 18,
            title = "Quick Dal",
            ingredients = listOf("red lentils", "turmeric", "cumin", "garlic", "onion", "tomatoes", "cilantro", "ghee"),
            instructions = listOf(
                "Rinse and cook lentils with turmeric.",
                "Make tempering with cumin and garlic.",
                "Add onions and tomatoes.",
                "Mix with cooked dal.",
                "Garnish with cilantro."
            ),
            cuisineType = "Indian",
            cookingTime = 20,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d"
        ),

        // More Mexican Recipes
        Recipe(
            id = 19,
            title = "Guacamole",
            ingredients = listOf("avocados", "lime", "onion", "tomatoes", "cilantro", "jalapeño", "salt", "tortilla chips"),
            instructions = listOf(
                "Mash avocados.",
                "Dice onions, tomatoes, and jalapeño.",
                "Mix ingredients together.",
                "Season with lime and salt.",
                "Serve with tortilla chips."
            ),
            cuisineType = "Mexican",
            cookingTime = 0,
            prepTime = 15,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegan", "vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1615870216519-2f9fa575fa5c"
        ),
        Recipe(
            id = 20,
            title = "Chiles Rellenos",
            ingredients = listOf("poblano peppers", "cheese", "eggs", "flour", "oil", "tomato sauce", "onion", "garlic"),
            instructions = listOf(
                "Roast and peel peppers.",
                "Stuff with cheese.",
                "Prepare egg batter.",
                "Coat peppers and fry.",
                "Serve with tomato sauce."
            ),
            cuisineType = "Mexican",
            cookingTime = 30,
            prepTime = 25,
            difficulty = Difficulty.HARD,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1599974579688-5d0f0c19f2bc"
        ),

        // More Italian Recipes
        Recipe(
            id = 21,
            title = "Risotto ai Funghi",
            ingredients = listOf("arborio rice", "mushrooms", "onion", "white wine", "parmesan", "butter", "stock", "garlic"),
            instructions = listOf(
                "Sauté mushrooms and set aside.",
                "Cook onions and rice.",
                "Add wine and stock gradually.",
                "Stir continuously until creamy.",
                "Finish with mushrooms and parmesan."
            ),
            cuisineType = "Italian",
            cookingTime = 35,
            prepTime = 15,
            difficulty = Difficulty.MEDIUM,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1595908129746-57ca1a63dd4d"
        ),
        Recipe(
            id = 22,
            title = "Tiramisu",
            ingredients = listOf("ladyfingers", "mascarpone", "coffee", "eggs", "sugar", "cocoa powder", "marsala wine"),
            instructions = listOf(
                "Make strong coffee and cool.",
                "Beat egg yolks and sugar.",
                "Mix in mascarpone.",
                "Dip ladyfingers in coffee.",
                "Layer and dust with cocoa."
            ),
            cuisineType = "Italian",
            cookingTime = 15,
            prepTime = 30,
            difficulty = Difficulty.MEDIUM,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9"
        ),

        // More Breakfast Recipes
        Recipe(
            id = 23,
            title = "Overnight Oats",
            ingredients = listOf("oats", "milk", "yogurt", "chia seeds", "honey", "berries", "nuts", "cinnamon"),
            instructions = listOf(
                "Mix oats, milk, and yogurt.",
                "Add chia seeds and honey.",
                "Refrigerate overnight.",
                "Top with berries and nuts.",
                "Add cinnamon to taste."
            ),
            cuisineType = "Breakfast",
            cookingTime = 0,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1586511925558-a4c6376fe65f"
        ),
        Recipe(
            id = 24,
            title = "Eggs Benedict",
            ingredients = listOf("eggs", "english muffins", "ham", "butter", "lemon juice", "vinegar", "salt", "pepper"),
            instructions = listOf(
                "Make hollandaise sauce.",
                "Poach eggs.",
                "Toast English muffins.",
                "Layer with ham and eggs.",
                "Top with hollandaise."
            ),
            cuisineType = "Breakfast",
            cookingTime = 20,
            prepTime = 15,
            difficulty = Difficulty.HARD,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1608039829572-78524f79c4c7"
        ),

        // Quick Mediterranean Recipes
        Recipe(
            id = 25,
            title = "Tabbouleh",
            ingredients = listOf("bulgur wheat", "parsley", "mint", "tomatoes", "onion", "lemon juice", "olive oil", "salt"),
            instructions = listOf(
                "Soak bulgur in hot water.",
                "Chop herbs and vegetables finely.",
                "Mix all ingredients.",
                "Season with lemon and oil.",
                "Chill before serving."
            ),
            cuisineType = "Mediterranean",
            cookingTime = 0,
            prepTime = 20,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegan", "vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1624300629298-e9de39c13be5"
        ),

        // Quick Asian Recipes
        Recipe(
            id = 26,
            title = "Miso Soup",
            ingredients = listOf("dashi", "miso paste", "tofu", "seaweed", "green onions", "mushrooms"),
            instructions = listOf(
                "Heat dashi stock.",
                "Dissolve miso paste.",
                "Add tofu and seaweed.",
                "Simmer briefly.",
                "Garnish with green onions."
            ),
            cuisineType = "Asian",
            cookingTime = 10,
            prepTime = 5,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd"
        ),

        // Quick Mexican Recipes
        Recipe(
            id = 27,
            title = "Quesadillas",
            ingredients = listOf("tortillas", "cheese", "chicken", "peppers", "onions", "salsa", "sour cream"),
            instructions = listOf(
                "Cook chicken and vegetables.",
                "Layer in tortilla with cheese.",
                "Cook until crispy.",
                "Cut into wedges.",
                "Serve with toppings."
            ),
            cuisineType = "Mexican",
            cookingTime = 10,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf(),
            imageUrl = "https://images.unsplash.com/photo-1599974579688-5d0f0c19f2bc"
        ),

        // Quick Italian Recipes
        Recipe(
            id = 28,
            title = "Bruschetta",
            ingredients = listOf("bread", "tomatoes", "garlic", "basil", "olive oil", "balsamic vinegar", "salt"),
            instructions = listOf(
                "Toast bread slices.",
                "Chop tomatoes and mix with garlic.",
                "Add torn basil leaves.",
                "Drizzle with oil and vinegar.",
                "Season to taste."
            ),
            cuisineType = "Italian",
            cookingTime = 5,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegan", "vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1572695157366-5e585ab2b69f"
        ),

        // Quick Indian Recipes
        Recipe(
            id = 29,
            title = "Raita",
            ingredients = listOf("yogurt", "cucumber", "mint", "cumin", "salt", "pepper", "onion"),
            instructions = listOf(
                "Grate cucumber and drain.",
                "Mix with yogurt.",
                "Add chopped mint and onion.",
                "Season with cumin and salt.",
                "Chill before serving."
            ),
            cuisineType = "Indian",
            cookingTime = 0,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1589010588553-46e8e7c21788"
        ),

        // Quick Breakfast
        Recipe(
            id = 32,
            title = "Smoothie Bowl",
            ingredients = listOf("frozen berries", "banana", "yogurt", "honey", "granola", "chia seeds", "coconut"),
            instructions = listOf(
                "Blend frozen fruit with yogurt.",
                "Pour into bowl.",
                "Top with granola and seeds.",
                "Add fresh fruit.",
                "Drizzle with honey."
            ),
            cuisineType = "Breakfast",
            cookingTime = 0,
            prepTime = 10,
            difficulty = Difficulty.EASY,
            dietaryPrefs = listOf("vegetarian"),
            imageUrl = "https://images.unsplash.com/photo-1590301157890-4810ed352733"
        )
    )

    // Get all recipes
    fun getAllRecipes(): List<Recipe> {
        return mockRecipes
    }
    
    // Add a new recipe
    fun addRecipe(recipe: Recipe): Recipe {
        val newId = mockRecipes.maxOf { it.id } + 1
        val newRecipe = recipe.copy(id = newId)
        mockRecipes.add(newRecipe)
        return newRecipe
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