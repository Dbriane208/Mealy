package daniel.brian.mealy.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.brian.mealy.model.remote.DrinkDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDrink(drink: DrinkDetails)

    @Delete
    suspend fun deleteDrink(drink: DrinkDetails)

    @Query("SELECT * FROM DrinkTable")
    fun getAllDrinks(): Flow<List<DrinkDetails>>
}

class DrinkDaoImpl: DrinkDao{
    private var drinks = mutableListOf<DrinkDetails>()

    override suspend fun upsertDrink(drink: DrinkDetails) {
        val existingIndex = drinks.indexOfFirst { it.idDrink == drink.idDrink }
        if (existingIndex != -1) drinks.removeAt(existingIndex)
    }

    override suspend fun deleteDrink(drink: DrinkDetails) {
        drinks.remove(drink)
    }

    override fun getAllDrinks(): Flow<List<DrinkDetails>> {
        return flowOf(drinks)
    }

}