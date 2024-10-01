package daniel.brian.mealy.repository

import daniel.brian.mealy.model.Category
import daniel.brian.mealy.model.CategoryResponse
import daniel.brian.mealy.network.KtorClient
import daniel.brian.mealy.utils.NetworkResult
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get

class CategoryRepository {
    private val client = KtorClient()

    suspend fun getCategories(): NetworkResult<List<Category>> =
        try {
            val response: CategoryResponse = client.httpClient
                .get("https://www.themealdb.com/api/json/v1/1/categories.php")
                .body()
            Napier.d("Fetched data: $response", tag = "data")
            NetworkResult.Success(response.categories)
        } catch (e: Exception) {
            NetworkResult.Error(e.message.toString())
        }finally {
            client.httpClient.close()
        }
}