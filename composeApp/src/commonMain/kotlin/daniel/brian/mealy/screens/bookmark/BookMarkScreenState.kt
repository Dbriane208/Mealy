package daniel.brian.mealy.screens.bookmark

import daniel.brian.mealy.model.remote.DrinkDetails
import daniel.brian.mealy.model.remote.Meal

data class BookMarkScreenState(
    val errorMessage: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val savedDrinks: List<DrinkDetails> = emptyList(),
    val savedMeals: List<Meal> = emptyList()
)
