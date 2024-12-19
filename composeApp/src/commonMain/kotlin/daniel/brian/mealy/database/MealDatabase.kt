package daniel.brian.mealy.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import daniel.brian.mealy.model.remote.DrinkDetails
import daniel.brian.mealy.model.remote.Meal

@Database(entities = [Meal::class, DrinkDetails::class], exportSchema = true, version = 1)
@ConstructedBy(MealDatabaseConstructor::class)
abstract class MealDatabase : RoomDatabase(){
    abstract fun mealDao(): MealDao
    abstract fun drinkDao(): DrinkDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object MealDatabaseConstructor : RoomDatabaseConstructor<MealDatabase> {
    override fun initialize(): MealDatabase
}
