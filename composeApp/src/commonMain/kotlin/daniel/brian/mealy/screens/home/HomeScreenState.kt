package daniel.brian.mealy.screens.home

import daniel.brian.mealy.model.Category

data class HomeScreenState (
    val categories: List<Category> = emptyList(),
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val error: Boolean = false
)