package daniel.brian.mealy.screens.details.category

import daniel.brian.mealy.model.remote.CategoryDetails

data class CategoryScreenState (
    val meals: List<CategoryDetails> = emptyList(),
    val errorMessage: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false
)