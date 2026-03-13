# 🌸 Daily Motivation — Anime Quotes App

A beautiful Android app featuring daily motivational quotes with stunning anime-inspired backgrounds and aesthetic UI design.

## ✨ Features

- 📱 **60+ Motivational Quotes** — Including famous quotes and anime-inspired ones
- 🎨 **Anime-inspired Backgrounds** — 8 beautiful gradient backgrounds (purple, sakura, ocean, sunset, forest, starlit, dawn, azure)
- ✨ **Glassmorphism UI** — Modern semi-transparent card design with blur effects
- 🔄 **Quote Navigation** — Swipe left/right or use buttons to browse quotes
- 🎲 **Random Quote** — Shuffle button for a random daily dose of motivation
- ⭐ **Favorites** — Save your favorite quotes with a heart tap
- 📤 **Share** — Share inspiring quotes to any app
- 🌙 **Dark/Light Theme** — Toggle between aesthetic dark and light modes
- 🏷️ **Categories** — Filter by Success, Life, Dreams, Perseverance, Happiness, Wisdom, Anime
- 🎬 **Smooth Animations** — Fade transitions, animated glowing orbs, swipe gestures

## 🛠 Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM |
| Database | Room (favorites storage) |
| DI | Hilt |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 |

## 📂 Project Structure

```
app/src/main/java/com/motivation/quotes/
├── data/
│   ├── model/           # Quote data class + enums
│   ├── local/           # Room DB, DAO, hardcoded quotes
│   └── repository/      # QuoteRepository
├── di/                  # Hilt AppModule
├── ui/
│   ├── components/      # Reusable UI: QuoteCard, AnimatedBackground
│   ├── navigation/      # NavGraph
│   ├── screens/         # HomeScreen, FavoritesScreen
│   └── theme/           # Colors, Typography, Theme
├── viewmodel/           # QuoteViewModel + UiState
├── MainActivity.kt
└── MotivationApp.kt
```

## 🚀 Setup Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17+
- Android SDK API 24–34

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Preetham7975/motivation-quotes-app.git
   cd motivation-quotes-app
   ```

2. **Open in Android Studio**
   - File → Open → select the cloned folder

3. **Sync Gradle**
   - Click "Sync Now" when prompted, or go to File → Sync Project with Gradle Files

4. **Build & Run**
   - Select a device/emulator (API 24+)
   - Click ▶️ Run, or press Shift+F10

### Adding Real Anime Backgrounds (Optional)

To replace the gradient backgrounds with actual anime images:
1. Place your images in `app/src/main/res/drawable/` (name them `bg_1.jpg`, `bg_2.jpg`, etc.)
2. In `AnimatedBackground.kt`, replace the gradient list with `Image` composables loading from drawable resources

## 📱 Screens

### Home Screen
- Beautiful anime-gradient background with floating orbs animation
- Glassmorphism quote card showing quote text, author, and category
- Category filter chips (horizontal scrollable)
- Navigation: Previous / Random / Next buttons
- Top bar: Theme toggle + Favorites (with count badge)

### Favorites Screen
- List of all saved favorite quotes
- Remove favorites with a single tap

## 🎨 Design System

| Element | Value |
|---------|-------|
| Card Style | Glassmorphism (semi-transparent white overlay) |
| Backgrounds | 8 anime-inspired multi-stop gradients |
| Typography | Serif for quotes, SansSerif for UI |
| Dark BG Colors | Deep navy/purple (#1A1A2E, #16213E) |
| Accent Colors | Purple (#7C4DFF), Pink (#FF4081), Blue (#448AFF) |

## 📄 License

MIT License — feel free to use, modify, and distribute.