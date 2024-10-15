package daniel.brian.mealy.screens.details.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import daniel.brian.mealy.components.CategoriesCard
import daniel.brian.mealy.components.CategoryCard
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

data class CategoryScreen(
    val categoryName: String
) : Screen {
    @Composable
    override fun Content() {
        val categoryViewModel = getViewModel(Unit, viewModelFactory { CategoryViewModel() })
        val categoryScreenState by categoryViewModel.categoryListUiState.collectAsState()

        LaunchedEffect(categoryViewModel){
            categoryViewModel.getCategoriesListItems(categoryName = categoryName)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(visible = categoryScreenState.meals.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp),
                ) {
                    items(categoryScreenState.meals) { category ->
                        CategoryCard(
                            category = category,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}