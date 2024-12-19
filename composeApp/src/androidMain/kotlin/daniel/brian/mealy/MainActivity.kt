package daniel.brian.mealy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import daniel.brian.mealy.database.getMealDatabase
import daniel.brian.mealy.screens.bookmark.BookmarkScreen
import daniel.brian.mealy.screens.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = getMealDatabase(applicationContext)
        val mealDao = database.mealDao()
        val drinkDao = database.drinkDao()

        BookmarkScreen.initialize(database)

        setContent {
            App(mealDao,drinkDao)
        }
    }
}
