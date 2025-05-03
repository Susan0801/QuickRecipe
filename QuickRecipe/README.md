# QuickRecipe

QuickRecipe is a modern Android application that helps users discover, browse, and save their favorite recipes. Built with Jetpack Compose, it provides a smooth and intuitive user experience for cooking enthusiasts.

## Key Features & Insights

- **Modern UI:** Built entirely with Jetpack Compose and Material3 for a responsive, declarative UI.
- **Recipe Discovery:** Browse, filter, and view detailed recipes with beautiful card-based UI.
- **Community & Social:** Create posts, share recipes, and interact with a community feed.
- **User Profile:** View and manage your profile, including a list of your created recipes and stats.
- **Reusable Components:** Dialogs, stat columns, and other UI elements are implemented as reusable composables.
- **Adaptive Design:** Responsive layouts and font sizes for phones and tablets.
- **Extensible Data Layer:** Repository pattern for easy migration from mock data to Room, DataStore, or cloud APIs.

## Architecture

QuickRecipe is built using:
- **MVVM architecture pattern** for clear separation of concerns
- **Repository pattern** for data management and abstraction
- **Composable UI** with Jetpack Compose
- **State management** using `mutableStateOf` and state hoisting

**Diagram:**
```
[ UI (Compose Screens & Components) ]
            |
            v
[ ViewModel (optional, for state/business logic) ]
            |
            v
[ Repository (UserRepository, PostRepository, MockRecipe) ]
            |
            v
[ Data Source (In-memory, Room, DataStore, API, etc.) ]
```

## Adaptive Design

- Uses window size awareness and utility functions for responsive padding and font sizes.
- Layouts adapt to different screen sizes and orientations for a great experience on any device.

## Reusable Components

- **CreatePostDialog:** Centralized dialog for post creation, used across the app.
- **StatColumn:** Displays user stats, easily reused for any similar data.
- **RecipeCard, PostItem, FilterChip:** Modular UI elements for consistent, maintainable code.

## Storage

- **In-Memory:** Mock repositories for rapid prototyping.
- **SharedPreferences:** Used for user session persistence.
- **Ready for Room/DataStore:** Architecture supports migration to persistent storage for production.
- **Planned Cloud Integration:** Firebase and cloud storage planned for future enhancements.

## External Interfaces

- **Coil:** Efficient image loading in Compose.
- **Firebase (planned):** For authentication, storage, and analytics.
- **Android Platform Services:** SharedPreferences, planned file/image storage.

## Project Structure

- `app/src/main/java/com/example/quickrecipe/`
  - `data/`: Data layer with repositories and models
  - `model/`: Domain models for the application
  - `ui/`: User interface components
    - `screens/`: Main application screens
    - `components/`: Reusable UI components
    - `theme/`: App theming

## Setup & Installation

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the application on an emulator or physical device

## Requirements

- Android Studio Arctic Fox (2020.3.1) or newer
- Android SDK 21+
- Kotlin 1.5.0+

## Future Enhancements

- User authentication
- Online recipe database integration
- Recipe search functionality
- Meal planning
- Shopping list generation from recipes
- Kitchen inventory management

## Reflection

This project demonstrates modern Android development best practices, including modular architecture, reusable components, and adaptive design. The codebase is clean, maintainable, and ready for future growth and integration with real data sources and cloud services.

## License

[License information to be added]

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. 