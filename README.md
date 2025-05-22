# Movie Discovery App

A modern Android application that allows users to discover movies using The Movie Database (TMDB) API. Built with Kotlin, Jetpack Compose, and following MVVM architecture with Clean Architecture principles.

## 🔑 API Key Setup

Before running the application, you need to obtain a TMDB API key and configure it:

1. **Get TMDB API Key:**
   - Visit [The Movie Database](https://www.themoviedb.org/)
   - Create an account or sign in
   - Go to Settings → API → Create → Developer
   - Fill in the required information to get your API key

2. **Configure API Key:**
   - Create a `local.properties` file in your project root directory (if it doesn't exist)
   - Add your API key to the file:
   ```properties
   API_KEY="your_tmdb_api_key_here"
   ```
   - Make sure `local.properties` is in your `.gitignore` file (it already is in this project)

**Note:** The app will not work without a valid API key. Make sure to replace `"your_tmdb_api_key_here"` with your actual TMDB API key.

## 📱 Features

### Core Features
- **Movie Categories**: Browse movies by Now Playing, Popular, Top Rated, and Upcoming
- **Movie Search**: Search for movies by title with real-time results
- **Movie Details**: View comprehensive movie information including:
  - Movie poster and backdrop images
  - Title, overview, and tagline
  - Release date and runtime
  - Genres and ratings
  - Vote average with star display
- **Multilingual Support**: English and Turkish language support
- **Dark/Light Theme**: Automatic theme switching based on system preferences
- **Infinite Scrolling**: Seamless pagination for all movie lists

### Enhanced User Experience
- **Beautiful UI**: Modern Material Design 3 with custom theming
- **Smooth Animations**: Loading animations and transitions
- **Error Handling**: Comprehensive error management with retry functionality
- **Responsive Design**: Optimized for different screen sizes
- **Network Optimization**: Efficient image loading with Coil

## 🛠️ Technology Stack

### Architecture & Patterns
- **MVVM Architecture**: Clean separation of concerns
- **Repository Pattern**: Centralized data management
- **Use Cases**: Business logic encapsulation
- **MVI-like Intent System**: Unidirectional data flow

### Libraries & Framework
| Library | Version | Purpose |
|---------|---------|---------|
| **Jetpack Compose** | BOM 2024.09.00 | Modern UI toolkit |
| **Hilt** | 2.55 | Dependency injection |
| **Retrofit** | 2.9.0 | Network requests |
| **OkHttp** | 4.11.0 | HTTP client with logging |
| **Coil** | 2.4.0 | Image loading |
| **Navigation Compose** | 2.7.0 | In-app navigation |
| **Coroutines** | 1.7.2 | Asynchronous programming |
| **Material 3** | Latest | UI components |
| **Gson** | 2.9.0 | JSON serialization |

### Testing
| Library | Purpose |
|---------|---------|
| **JUnit** | Unit testing framework |
| **Mockito** | Mocking framework |
| **MockK** | Kotlin-specific mocking |
| **Coroutines Test** | Testing coroutines |
| **Turbine** | Flow testing |
| **Truth** | Assertions |
| **Hilt Testing** | DI testing |

## 📁 Project Structure

```
app/src/main/java/com/example/moviediscovery/
├── data/
│   ├── api/           # API interfaces and models
│   ├── di/            # Dependency injection modules
│   ├── repository/    # Repository implementations
│   └── util/          # Data utilities
├── domain/
│   ├── model/         # Domain models
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Business logic use cases
├── presentation/
│   ├── base/          # Base classes
│   ├── common/        # Shared UI components
│   ├── detail/        # Movie details screen
│   ├── home/          # Home screen
│   ├── navigation/    # Navigation setup
│   ├── search/        # Search functionality
│   ├── settings/      # App settings
│   └── theme/         # UI theming
└── util/              # Utility classes
```


## 📱 Screenshots

### Light Theme
<table>
  <tr>
    <th>Home Screen</th>
    <th>Movie Details</th>
    <th>Error</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/9b96b78c-a992-45c6-9267-4bfa188b56c1" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/1847fe80-aed3-4a6e-b4a6-ccdd816ca1c6" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/6f8db3ec-464f-46b4-a21d-e8c6f698fe78" height="500"></td>
  </tr>
</table>

<table>
  <tr>
    <th>Home Screen 2</th>
    <th>Language Settings</th>
    <th>Loading Screen</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/c2562cc7-ef1e-4c1f-89ac-bd8c6fbc5de4" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/0fca1c39-3874-4f2f-bbca-747b732851f3" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/24619065-988f-4611-9673-24c442ecec97" height="500"></td>
  </tr>
</table>

### Dark Theme
<table>
  <tr>
    <th>Home Screen</th>
    <th>Movie Details</th>
    <th>Search Results</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/0edfdcb1-3354-4716-b9af-dda785c56648" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/77d93a25-8790-4dae-b6da-17e18dfff11e" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/16c73943-9192-450e-b719-686c0d28ae16" height="500"></td>
  </tr>
</table>

<table>
  <tr>
    <th>Home Screen 2</th>
    <th>Language Settings</th>
    <th>Search End State</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/730d55a4-f9a6-46a3-b3e8-a4a0da3f8b11" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/0f403a0b-2a8a-4683-a3a9-cf7209a394d3" height="500"></td>
    <td><img src="https://github.com/user-attachments/assets/259af289-17a9-4c74-886c-c13abb06bff3" height="500"></td>
  </tr>
</table>

### Demo Video
You can watch the demo video on YouTube:

[📽️ movieDiscovery.mp4](https://youtube.com/shorts/XLMa92lg6FU)

## 🏗️ Architecture Details

### Clean Architecture Layers
1. **Presentation Layer**: Compose UI, ViewModels, and UI state management
2. **Domain Layer**: Use cases, business logic, and domain models
3. **Data Layer**: Repository implementations, API services, and data models

### Key Design Patterns
- **Repository Pattern**: Abstracts data sources
- **Use Case Pattern**: Encapsulates business logic
- **Observer Pattern**: Reactive UI updates with StateFlow
- **Dependency Injection**: Loose coupling with Hilt
- **Single Source of Truth**: Centralized state management

### Network Architecture
- **Retrofit + OkHttp**: RESTful API communication
- **Gson**: JSON parsing
- **Error Handling**: Comprehensive error mapping and user feedback
- **Caching Strategy**: Efficient image caching with Coil

## 🌍 Internationalization

The app supports multiple languages:
- **English (Default)**
- **Turkish**

Language can be changed from the Settings screen, and the app will restart automatically to apply the new language.

## 🎨 Design System

### Material Design 3
- **Dynamic Colors**: Adapts to system theme
- **Custom Color Palette**: Carefully selected colors for movie app
- **Typography Scale**: Consistent text styling
- **Shape System**: Rounded corners and modern aesthetics

### Theming
- **Light Theme**: Clean and bright interface
- **Dark Theme**: OLED-friendly dark colors
- **System Theme**: Automatic switching based on device settings

## 📊 Performance Optimizations

- **Image Loading**: Efficient caching and loading with Coil
- **Memory Management**: Proper lifecycle handling
- **Network Optimization**: Request batching and caching
- **UI Performance**: Compose best practices and recomposition optimization
