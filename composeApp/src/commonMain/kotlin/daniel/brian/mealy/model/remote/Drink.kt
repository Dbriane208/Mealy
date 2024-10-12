package daniel.brian.mealy.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class Drink (
    val strDrink: String?,
    val strDrinkThumb: String?,
    val idDrink: String?
)

@Serializable
data class DrinkList (
    val drinks: List<Drink>
)