package daniel.brian.mealy.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.mealy.repository.HomeRepository
import daniel.brian.mealy.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel: ViewModel() {
    private val homeRepository = HomeRepository()

    private val _searchUiState = MutableStateFlow(SearchScreenState())
    val searchUiState: StateFlow<SearchScreenState> = _searchUiState.asStateFlow()

    fun searchMealList(search: String) {
        viewModelScope.launch {
            if (search.isBlank()) {
                _searchUiState.update {
                    it.copy(
                        searchedMeals = emptyList(),
                        isLoading = false,
                        error = false
                    )
                }
                return@launch
            }

            when(val searches = homeRepository.searchMeal(search)){
                is NetworkResult.Error -> {
                    _searchUiState.update {
                        it.copy(
                            errorMessage = searches.message.toString(),
                            isLoading = false,
                            error = true
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _searchUiState.update {
                        it.copy(
                            isLoading = true,
                            error = false
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _searchUiState.update {
                        it.copy(
                            searchedMeals = searches.data ?: emptyList(),
                            isLoading = false,
                            error = false
                        )
                    }
                }
            }
        }
    }
}