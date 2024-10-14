package daniel.brian.mealy.screens.details.drink

import daniel.brian.mealy.repository.DetailsRepository
import daniel.brian.mealy.utils.NetworkResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DrinkDetailsViewModel: ViewModel() {
    private val detailsRepository = DetailsRepository()

    private val _drinksUiState = MutableStateFlow(DrinkDetailsState())
    val drinkUiState: StateFlow<DrinkDetailsState> = _drinksUiState.asStateFlow()

    fun getDrinks(drinkId: Int) {
        viewModelScope.launch {
            when(val drinkDetails = detailsRepository.getDrinkDetails(drinkId)){
                is NetworkResult.Error -> {
                    _drinksUiState.update {
                        it.copy(
                            error = true,
                            errorMessage = drinkDetails.message.toString()
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _drinksUiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is NetworkResult.Success -> {
                    _drinksUiState.update {
                        it.copy(drink = drinkDetails.data?.firstOrNull())
                    }
                }
            }
        }
    }
}