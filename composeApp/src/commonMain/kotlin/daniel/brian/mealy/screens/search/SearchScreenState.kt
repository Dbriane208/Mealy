package daniel.brian.mealy.screens.search

import daniel.brian.mealy.model.remote.Meal

data class SearchScreenState(
    val searchedMeals: List<Meal> = emptyList(),
    val errorMessage: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false
)
