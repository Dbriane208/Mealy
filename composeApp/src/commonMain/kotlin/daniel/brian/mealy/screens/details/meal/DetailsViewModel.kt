package daniel.brian.mealy.screens.details.meal

import daniel.brian.mealy.repository.DetailsRepository
import daniel.brian.mealy.utils.NetworkResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    private val detailsRepository = DetailsRepository()

    private val _detailsUiState = MutableStateFlow(DetailsScreenState())
    val detailsUiState: StateFlow<DetailsScreenState> = _detailsUiState.asStateFlow()

    fun getMeals(mealId: Int) {
        viewModelScope.launch {
            when(val mealDetails = detailsRepository.getMealDetails(mealId)){
                is NetworkResult.Error -> {
                    _detailsUiState.update {
                        it.copy(
                            errorMessage = mealDetails.message.toString(),
                            error = true
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _detailsUiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is NetworkResult.Success -> {
                    _detailsUiState.update {
                        it.copy(meal = mealDetails.data?.firstOrNull())
                    }
                }
            }
        }
    }
}