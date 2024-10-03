package daniel.brian.mealy.repository

import daniel.brian.mealy.model.Category
import daniel.brian.mealy.model.CategoryListResponse
import daniel.brian.mealy.model.Drink
import daniel.brian.mealy.model.DrinkList
import daniel.brian.mealy.model.Meal
import daniel.brian.mealy.model.MealListResponse
import daniel.brian.mealy.network.KtorClient
import daniel.brian.mealy.utils.NetworkResult
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class HomeRepository {
    private val client = KtorClient()

    suspend fun getCategories(): NetworkResult<List<Category>> =
        try {
            val response: CategoryListResponse = client.httpClient
                .get("https://www.themealdb.com/api/json/v1/1/categories.php")
                .body()
            Napier.d("Fetched data: ${response.categories}")
            NetworkResult.Success(response.categories)
        } catch (e: Exception) {
            NetworkResult.Error(e.message.toString())
        }

    suspend fun getRandomMeal(): NetworkResult<List<Meal>> =
        try {
            val meal: MealListResponse = client.httpClient
                .get("https://www.themealdb.com/api/json/v1/1/random.php")
                .body()
            Napier.d("Meal:${meal.meals}")
            NetworkResult.Success(meal.meals)
        }catch (e: Exception){
            NetworkResult.Error(e.message.toString())
        }

    suspend fun getNonAlcoholicDrinks(): NetworkResult<List<Drink>> =
        try {
            val drink: DrinkList = client.httpClient
                .get("https://www.thecocktaildb.com/api/json/v1/1/filter.php"){
                    parameter("a","Non_Alcoholic")
                }
                .body()
            Napier.d("Drink data:${drink.drinks}")
            NetworkResult.Success(drink.drinks)
        }catch (e: Exception) {
            NetworkResult.Error(e.message.toString())
        }
}