package daniel.brian.mealy.screens.home

import daniel.brian.mealy.repository.HomeRepository
import daniel.brian.mealy.utils.NetworkResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val homeRepository = HomeRepository()

    private val _homeUiState = MutableStateFlow(HomeScreenState())
    val homeUiState: StateFlow<HomeScreenState> = _homeUiState.asStateFlow()

    fun getAllCategories() {
        viewModelScope.launch {
            when(val categories = homeRepository.getCategories()){
                is NetworkResult.Error -> {
                    _homeUiState.update {
                        it.copy(
                            errorMessage = categories.message.toString(),
                            isLoading = false,
                            error = true
                        )
                    }
                }
                is NetworkResult.Success -> {
                    _homeUiState.update {
                        it.copy(
                            categories = categories.data ?: emptyList(),
                            isLoading = false,
                            error = false
                        )
                    }
                }
            }
        }
    }

    fun getRandomMeal() {
        viewModelScope.launch {
            when(val randomMeal = homeRepository.getRandomMeal()){
                is NetworkResult.Error -> {
                    _homeUiState.update {
                        it.copy(
                            errorMessage = randomMeal.message.toString(),
                            isLoading = false,
                            error = true
                        )
                    }
                }
                is NetworkResult.Success -> {
                    _homeUiState.update {
                        it.copy(
                            meals = randomMeal.data ?: emptyList(),
                            isLoading = false,
                            error = false
                        )
                    }
                }
            }
        }
    }

    fun getNonAlcoholicDrinks() {
        viewModelScope.launch {
            when(val drink = homeRepository.getNonAlcoholicDrinks()){
                is NetworkResult.Error -> {
                    _homeUiState.update {
                        it.copy(
                            errorMessage = drink.message.toString(),
                            isLoading = false,
                            error = true
                        )
                    }
                }
                is NetworkResult.Success -> {
                    _homeUiState.update {
                        it.copy (
                            drinks = drink.data ?: emptyList(),
                            isLoading = false,
                            error = false
                        )
                    }
                }
            }
        }
    }
}
