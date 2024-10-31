package daniel.brian.mealy.screens.home

import daniel.brian.mealy.model.remote.Meal
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

    private var randomSavedState: Meal? = null

    private fun storeRandomMeal(meal: Meal) {
        randomSavedState = meal
    }

    init{
        getAllCategories()
        getRandomMeal()
        getNonAlcoholicDrinks()
    }

    private fun getAllCategories() {
       viewModelScope.launch {
           _homeUiState.update {
               it.copy(isCategoriesLoading = true)
           }

            when(val categories = homeRepository.getCategories()){
                is NetworkResult.Loading -> {
                    _homeUiState.update {
                        it.copy(
                            isCategoriesLoading = true,
                            error = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _homeUiState.update {
                        it.copy(
                            errorMessage = categories.message.toString(),
                            isCategoriesLoading = false,
                            error = true
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _homeUiState.update {
                        it.copy(
                            categories = categories.data ?: emptyList(),
                            isCategoriesLoading = false,
                            error = false,
                        )
                    }
                }
            }
       }
    }

    private fun getRandomMeal() {
        viewModelScope.launch {
            // checking whether we have stored a random meal
            randomSavedState?.let { savedMeal ->
                _homeUiState.update {
                    it.copy(
                        meals = listOf(savedMeal)
                    )
                }
                // Exit the coroutine early if we have a saved meal
                return@launch
            }

            // If we don't have a stored meal, we fetch one
            _homeUiState.update {
                it.copy(isRandomLoading = true)
            }

            when(val randomMeal = homeRepository.getRandomMeal()){
                is NetworkResult.Loading -> {
                    _homeUiState.update {
                        it.copy(
                            isRandomLoading = true,
                            error = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _homeUiState.update {
                        it.copy(
                            errorMessage = randomMeal.message.toString(),
                            isRandomLoading = false,
                            error = true
                        )
                    }
                }

                is NetworkResult.Success -> {
                    randomMeal.data?.firstOrNull()?.let { meal ->
                        storeRandomMeal(meal = meal)

                        _homeUiState.update {
                            it.copy(
                                meals = listOf(meal),
                                isRandomLoading = false,
                                error = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getNonAlcoholicDrinks() {
        viewModelScope.launch {
            _homeUiState.update {
                it.copy(isDrinksLoading = true)
            }

            when(val drink = homeRepository.getNonAlcoholicDrinks()){
                is NetworkResult.Loading -> {
                    _homeUiState.update {
                        it.copy(
                            isDrinksLoading = true,
                            error = false
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _homeUiState.update {
                        it.copy(
                            errorMessage = drink.message.toString(),
                            isDrinksLoading = false,
                            error = true
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _homeUiState.update {
                        it.copy (
                            drinks = drink.data ?: emptyList(),
                            isDrinksLoading = false,
                            error = false
                        )
                    }
                }
            }
        }
    }
}
