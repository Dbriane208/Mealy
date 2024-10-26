package daniel.brian.mealy.screens.home

import daniel.brian.mealy.model.remote.Category
import daniel.brian.mealy.model.remote.Drink
import daniel.brian.mealy.model.remote.Meal

data class HomeScreenState (
    val categories: List<Category> = emptyList(),
    val meals: List<Meal> = emptyList(),
    val drinks: List<Drink> = emptyList(),
    val errorMessage: String = "",
    val randomMeal: Meal? = null,
    val isCategoriesLoading: Boolean = false,
    val isRandomLoading: Boolean = false,
    val isDrinksLoading: Boolean = false,
    val error: Boolean = false
)
