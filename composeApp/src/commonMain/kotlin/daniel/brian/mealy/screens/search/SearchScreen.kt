package daniel.brian.mealy.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import daniel.brian.mealy.components.SavedMealCard
import daniel.brian.mealy.database.MealDaoImpl
import daniel.brian.mealy.screens.details.meal.DetailsScreen
import daniel.brian.mealy.utils.DrinksCardShimmerEffect

object SearchScreen : Tab {
    private val searchScreenViewModel: SearchScreenViewModel by lazy {
        SearchScreenViewModel()
    }
    private val mealDao = MealDaoImpl()

    @Composable
    override fun Content() {
        val searchScreenState by searchScreenViewModel.searchUiState.collectAsState()
        var searchWord by remember { mutableStateOf("") }
        val navigator = LocalNavigator.current

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "Search Your  Favorite Meal",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .padding(start = 10.dp, end = 10.dp, top = 30.dp),
                    value = searchWord,
                    onValueChange = {
                        searchWord = it
                        if(it.isEmpty()){
                            searchScreenViewModel.searchMealList("")
                        }
                    },
                    label = { Text("Search a Meal") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            searchScreenViewModel.searchMealList(searchWord)
                        }
                    )
                )

                IconButton(
                    modifier = Modifier.padding(top = 40.dp),
                    onClick = {
                        searchScreenViewModel.searchMealList(searchWord)
                    },
                ){
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 130.dp)
            ){
                when {
                    searchScreenState.isLoading -> {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                columns = GridCells.Adaptive(100.dp)
                            ) {
                                items(count = searchScreenState.searchedMeals.size) {
                                    Card(
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.size(100.dp)
                                    ) {
                                        DrinksCardShimmerEffect(modifier = Modifier)
                                    }
                                }
                            }
                        }
                    }

                    searchScreenState.error -> {
                        Text(
                            text = "Error: ${searchScreenState.errorMessage}",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Red
                        )
                    }

                    else -> {
                        AnimatedVisibility(
                            visible = searchScreenState.searchedMeals.isNotEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(100.dp),
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier
                            ) {
                                items(searchScreenState.searchedMeals) { search ->
                                    SavedMealCard(
                                        modifier = Modifier,
                                        meal = search,
                                        onClick = { item ->
                                            navigator?.push(DetailsScreen(item, mealDao))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Rounded.Search)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Search",
                    icon = icon
                )
            }
        }

}