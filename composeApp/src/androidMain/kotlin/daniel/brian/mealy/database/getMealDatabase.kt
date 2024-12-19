package daniel.brian.mealy.database

import android.content.Context
import androidx.room.Room

fun getMealDatabase(context: Context): MealDatabase {
    val appContext = context.applicationContext
    val dbFile = context.getDatabasePath("Meal.db")
    val builder = Room.databaseBuilder(
        context = appContext,
        klass = MealDatabase::class.java,
        name = dbFile.absolutePath
    )
    return getRoomDatabase(builder)
}


