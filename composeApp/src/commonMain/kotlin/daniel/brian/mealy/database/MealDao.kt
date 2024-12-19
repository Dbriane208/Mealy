package daniel.brian.mealy.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.brian.mealy.model.remote.DrinkDetails
import daniel.brian.mealy.model.remote.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM MealTable")
    fun getAllMeals(): Flow<List<Meal>>
}

class MealDaoImpl: MealDao {
    private var meals = mutableListOf<Meal>()

    override suspend fun upsert(meal: Meal) {
        val existingIndex = meals.indexOfFirst { it.idMeal == meal.idMeal }
        if (existingIndex != -1) meals[existingIndex] = meal
        else meals.add(meal)
    }


    override suspend fun delete(meal: Meal) {
        meals.remove(meal)
    }

    override fun getAllMeals(): Flow<List<Meal>> {
        return flowOf(meals)
    }

}