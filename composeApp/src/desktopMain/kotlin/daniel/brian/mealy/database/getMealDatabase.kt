package daniel.brian.mealy.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

fun getMealDatabase(): MealDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "Meal.db")
    val driver = BundledSQLiteDriver()
    return Room.databaseBuilder<MealDatabase>(
        name = dbFile.absolutePath
    )   .setDriver(driver)
        .build()
}
