package daniel.brian.mealy.repository

import daniel.brian.mealy.model.remote.Meal
import daniel.brian.mealy.model.remote.MealListResponse
import daniel.brian.mealy.network.KtorClient
import daniel.brian.mealy.utils.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class DetailsRepository {
    private val client = KtorClient()

    suspend fun getMealDetails(id: Int): NetworkResult<List<Meal>> =
        try {
            val mealDetails: MealListResponse = client.httpClient
                .get("https://www.themealdb.com/api/json/v1/1/lookup.php"){
                    parameter("i",id)
                }
                .body()
            NetworkResult.Success(mealDetails.meals)
            NetworkResult.Success(mealDetails.meals)
        }catch (e: Exception) {
            NetworkResult.Error(e.message.toString())
        }
}