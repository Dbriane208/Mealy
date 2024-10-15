package daniel.brian.mealy.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDetails(
    val strMeal: String?,
    val strMealThumb: String?,
    val idMeal: String?
)

@Serializable
data class CategoryDetailsList(
    val meals: List<CategoryDetails>
)