package daniel.brian.mealy.screens.home

import daniel.brian.mealy.model.Category
import daniel.brian.mealy.model.Drink
import daniel.brian.mealy.model.Meal

data class HomeScreenState (
    val categories: List<Category> = emptyList(),
    val meals: List<Meal> = emptyList(),
    val drinks: List<Drink> = emptyList(),
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val error: Boolean = false
)