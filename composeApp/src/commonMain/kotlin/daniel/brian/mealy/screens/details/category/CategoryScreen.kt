package daniel.brian.mealy.screens.details.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import daniel.brian.mealy.components.CategoryCard
import daniel.brian.mealy.screens.details.meal.DetailsScreen
import daniel.brian.mealy.utils.DrinksCardShimmerEffect

data class CategoryScreen(
    val categoryName: String
) : Screen {
    private val categoryViewModel: CategoryViewModel by lazy {
        CategoryViewModel(categoryName = categoryName)
    }

    @Composable
    override fun Content() {
        val categoryScreenState by categoryViewModel.categoryListUiState.collectAsState()
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                TopAppBar(
                    contentColor = Color.White,
                    backgroundColor = Color.Gray,
                    title = {Text("$categoryName Category Meals")},
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator?.pop()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedVisibility(
                    visible = categoryScreenState.isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Adaptive(100.dp)
                    ) {
                        items(count = categoryScreenState.meals.size) {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(100.dp)
                            ) {
                                DrinksCardShimmerEffect(modifier = Modifier)
                            }
                        }
                    }
                }

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
                                onClick = { meal ->
                                    navigator?.push(DetailsScreen(meal))
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

        }
    }
}
