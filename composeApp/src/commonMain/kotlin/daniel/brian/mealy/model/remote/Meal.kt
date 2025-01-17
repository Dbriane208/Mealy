package daniel.brian.mealy.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "MealTable")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strYoutube: String? ,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strMeasure1: String? ,
    val strMeasure2: String? ,
    val strMeasure3: String? ,
    val strMeasure4: String? ,
    val strMeasure5: String? ,
    val strMeasure6: String? ,
    val strMeasure7: String? ,
    val strMeasure8: String? ,
    val strMeasure9: String? ,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strSource: String?
)

@Serializable
data class MealListResponse(
    val meals: List<Meal>
)
