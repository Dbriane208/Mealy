package daniel.brian.mealy.screens.details.meal

import daniel.brian.mealy.model.remote.Meal

data class DetailsScreenState (
    val meal: Meal? = null,
    val errorMessage: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false
)