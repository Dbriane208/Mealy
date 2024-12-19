package daniel.brian.mealy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import daniel.brian.mealy.database.getMealDatabase
import daniel.brian.mealy.screens.bookmark.BookmarkScreen

fun main() = application {
    val database = getMealDatabase()
    val mealDao = database.mealDao()
    val drinkDao = database.drinkDao()

    BookmarkScreen.initialize(database)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Mealy",
    ) {
        App(mealDao,drinkDao)
    }
}