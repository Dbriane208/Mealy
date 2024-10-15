package daniel.brian.mealy.screens.details.drink

import daniel.brian.mealy.model.remote.DrinkDetails

data class DrinkDetailsState(
    val drink: DrinkDetails? = null,
    val errorMessage: String = "",
    val error: Boolean = false,
    val isLoading: Boolean = false
)
