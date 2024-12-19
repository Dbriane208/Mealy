package daniel.brian.mealy.screens.details.meal

import daniel.brian.mealy.repository.DetailsRepository
import daniel.brian.mealy.utils.NetworkResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    private val detailsRepository = DetailsRepository()

    private val _detailsUiState = MutableStateFlow(DetailsScreenState())
    val detailsUiState: StateFlow<DetailsScreenState> get() = _detailsUiState

    fun getMeals(mealId: Int) {
        viewModelScope.launch {
            _detailsUiState.update {
                it.copy(isLoading = true)
            }

            Napier.d("Fetching meal details for ID: $mealId")
            when(val mealDetails = detailsRepository.getMealDetails(mealId)){
                is NetworkResult.Error -> {
                    _detailsUiState.update {
                        it.copy(
                            errorMessage = mealDetails.message.toString(),
                            error = true,
                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    Napier.d("Loading meal details")
                    _detailsUiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is NetworkResult.Success -> {
                    val fetchedMeal = mealDetails.data?.firstOrNull()
                    Napier.d("Meal data: ${mealDetails.data}")
                    Napier.d("Fetched data: $fetchedMeal")
                    _detailsUiState.update {
                        it.copy(
                            meal = fetchedMeal,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}