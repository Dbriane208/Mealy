package daniel.brian.mealy.screens.home

import daniel.brian.mealy.repository.CategoryRepository
import daniel.brian.mealy.utils.NetworkResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val categoryRepository = CategoryRepository()

    private val _homeUiState = MutableStateFlow(HomeScreenState())
    val homeUiState: StateFlow<HomeScreenState> = _homeUiState.asStateFlow()

    fun getAllCategories() {
        viewModelScope.launch {
            when(val categories = categoryRepository.getCategories()){
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
}
