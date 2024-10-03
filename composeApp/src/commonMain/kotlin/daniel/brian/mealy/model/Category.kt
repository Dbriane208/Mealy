package daniel.brian.mealy.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("idCategory")
    val categoryId: String,
    @SerialName("strCategory")
    val categoryName: String,
    @SerialName("strCategoryThumb")
    val categoryImg: String,
    @SerialName("strCategoryDescription")
    val categoryDescription: String? = null
)

@Serializable
data class CategoryListResponse(
    val categories: List<Category>
)
