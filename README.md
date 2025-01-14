# Mealy! 🍴🍲🍹🍸

A Compose Multiplatform app built with Jetpack Compose to run on Android and Desktop consuming [COCKTAIL](https://www.thecocktaildb.com/) and the [MEAL](https://www.themealdb.com/) APIs to help fetch food and drinks. The app basically demonstrates the use of Room database, the MVVM architecture, cross-platform development and how to consume a RESTFUL api and handle the responses by displaying them in the UI.

# Features
-  ✅Displaying different types of food in various categories
-  ✅Adding different kinds of food and drinks to the favorite section
-  ✅Deleting of items from the favorite and cart section

# Tech Stack

- <b>[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html):</b> The Kotlin Multiplatform technology is designed to simplify the development of cross-platform projects.
- <b>[Moko MVVM](https://github.com/icerockdev/moko-mvvm)</b>: This is a Kotlin Multiplatform library that provides architecture components of Model-View-ViewModel for UI applications. Components are lifecycle-aware on Android.
- <b>[Ktor](https://ktor.io/docs/client-create-multiplatform-application.html/)</b>: It's a straightforward network library used for network communications. This library allows us to easily capture JSON responses from web services and web APIs.Used for making network requests to fetch data from the [CocktailDB](https://www.thecocktaildb.com/) and the [MealDB](https://www.themealdb.com/) APIs.
- <b>[Kotlin Flow](https://developer.android.com/kotlin/flow)</b>: Kotlin flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
- <b>[Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)</b>: A design pattern for concurrency that you can apply to Android to make asynchronous code execution simpler.
- <b>[Room](https://developer.android.com/training/data-storage/sqlite)</b>: The Room persistence library enables fluid database access while utilizing all of SQLite's capabilities by providing an abstraction layer over SQLite,.
- <b>[Napier](https://github.com/AAkira/Napier)</b>: Napier is a logger library for Kotlin Multiplatform It supports Android, Darwin(iOS, macOS, watchOS, tvOS), JVM, JavaScript. Logs written in common module are displayed on logger viewer of each platform.
- <b>[Kamel](https://github.com/Kamel-Media/Kamel)</b>: Kamel is an asynchronous media loading library for Compose Multiplatform. It provides a simple, customizable and efficient way to load, cache, decode and display images in your application. By default, it uses Ktor client for loading resources.
- <b>[Voyager](https://voyager.adriel.cafe/)</b>: A multiplatform navigation library built for, and seamlessly integrated with, Jetpack Compose.
  

## Setup Instructions

- Clone the repository to your local machine.
- Open the project in Android Studio.
  
    - <b> Android</b>: To run the application on android device/emulator. Open project in Android Studio and run imported android run configuration
    - <b>Desktop</b>: Run the desktop application in the terminal: <b>./gradlew run</b>


# Project Images
- ## Android
<div style="display:flex;">
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/homepage.png" alt="auth_start" width="200"/>
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/detailspage.png" alt="auth_reg_login" width="200"/>
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/savedmeals.png" alt="auth_login" width="200"/>
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/searchpage.png" alt="auth_reg" width="200">
</div>

- ## Desktop
<div style="display:flex;flex-direction:column;align-items:start;">
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/desktophome.png" alt="home" width="1000"/>
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/desktopdetails.png" alt="details" width="1000"/>
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/desktopSaved.png" alt="favorites" width="1000"/>
    <img src="https://github.com/Dbriane208/Mealy/blob/main/composeApp/images/desktopSearch.png" alt="search" width="1000">
</div>
  
    
## Contributing
If you would like to contribute to this project, feel free to fork the repository, make your changes, and create a pull request. Your contributions is much appreciated!
