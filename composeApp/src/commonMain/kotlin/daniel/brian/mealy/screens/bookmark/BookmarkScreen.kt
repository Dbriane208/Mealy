package daniel.brian.mealy.screens.bookmark

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import daniel.brian.mealy.components.SavedDrinkCard
import daniel.brian.mealy.components.SavedMealCard
import daniel.brian.mealy.database.DrinkDao
import daniel.brian.mealy.database.DrinkDaoImpl
import daniel.brian.mealy.database.MealDao
import daniel.brian.mealy.database.MealDaoImpl
import daniel.brian.mealy.database.MealDatabase
import daniel.brian.mealy.screens.details.drink.DrinkDetailsScreen
import daniel.brian.mealy.screens.details.meal.DetailsScreen
import daniel.brian.mealy.utils.DrinksCardShimmerEffect
import io.github.aakira.napier.Napier
import mealy.composeapp.generated.resources.Res

object BookmarkScreen : Tab {
    private lateinit var mealDao: MealDao
    private lateinit var drinkDao: DrinkDao

    fun initialize(database: MealDatabase){
        mealDao = database.mealDao()
        drinkDao = database.drinkDao()
    }

    @Composable
    override fun Content() {
        val bookMarkScreenState = BookMarkScreenState()
        val navigator = LocalNavigator.current

        val meals by mealDao.getAllMeals().collectAsState(initial = emptyList())
        val drinks by drinkDao.getAllDrinks().collectAsState(initial = emptyList())
        
        Scaffold(
            topBar = {
                TopAppBar(
                    contentColor = Color.White,
                    backgroundColor = Color.Gray,
                    title = { Text(text = "Favorite Meals") }
                )
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedVisibility(
                    visible = bookMarkScreenState.isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Adaptive(100.dp)
                    ) {
                        items(count = meals.size + drinks.size) {
                            Napier.d("All meals size ${meals.size}")
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(100.dp)
                            ) {
                                DrinksCardShimmerEffect(modifier = Modifier)
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = meals.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp),
                    ) {
                        // Meals section
                        if (meals.isNotEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Saved Meals",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            items(meals) { meal ->
                                SavedMealCard(
                                    meal = meal,
                                    onClick = { item ->
                                        navigator?.push(DetailsScreen(item, mealDao))
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // Drinks section
                        if (drinks.isNotEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Saved Drinks",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                )
                            }
                            items(drinks) { drink ->
                                SavedDrinkCard(
                                    drink = drink,
                                    onClick = { item ->
                                        navigator?.push(DrinkDetailsScreen(item, drinkDao))
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

    override val options: TabOptions
       @Composable
       get(){
           val icon = rememberVectorPainter(Icons.Outlined.Favorite)
          return remember {
              TabOptions(
                  index = 2u,
                  title = "Bookmark",
                  icon = icon
              )
          }
       }
}