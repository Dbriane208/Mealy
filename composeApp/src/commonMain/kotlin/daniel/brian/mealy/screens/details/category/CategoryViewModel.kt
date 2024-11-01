package daniel.brian.mealy.screens.details.category

import daniel.brian.mealy.repository.DetailsRepository
import daniel.brian.mealy.utils.NetworkResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(categoryName: String) : ViewModel() {
    private val detailsRepository = DetailsRepository()

    private val _categoryListUiState = MutableStateFlow(CategoryScreenState())
    val categoryListUiState: StateFlow<CategoryScreenState> = _categoryListUiState.asStateFlow()

    init {
        getCategoriesListItems(categoryName = categoryName)
    }

    private fun getCategoriesListItems(categoryName: String){
        viewModelScope.launch {

            _categoryListUiState.update { it.copy(isLoading = true) }

            when(val categoryListItems = detailsRepository.getCategoriesList(categoryName = categoryName)){
                is NetworkResult.Error -> {
                    _categoryListUiState.update {
                        it.copy(
                            error = true,
                            errorMessage = categoryListItems.message.toString(),
                            isLoading = false
                        )
                    }
                }

                is NetworkResult.Loading -> {
                    _categoryListUiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is NetworkResult.Success -> {
                    _categoryListUiState.update {
                        it.copy(
                            meals = categoryListItems.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}