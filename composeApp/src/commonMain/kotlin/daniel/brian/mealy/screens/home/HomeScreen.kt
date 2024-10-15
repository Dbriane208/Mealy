package daniel.brian.mealy.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import daniel.brian.mealy.components.CategoriesCard
import daniel.brian.mealy.components.DrinksCard
import daniel.brian.mealy.components.RandomCard
import daniel.brian.mealy.screens.details.category.CategoryScreen
import daniel.brian.mealy.screens.details.drink.DrinkDetailsScreen
import daniel.brian.mealy.screens.details.meal.DetailsScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

object HomeScreen : Tab {
    @Composable
    override fun Content() {
        val homeViewModel = getViewModel(Unit, viewModelFactory { HomeViewModel() })
        val homeScreenState by homeViewModel.homeUiState.collectAsState()

        LaunchedEffect(homeViewModel){
            homeViewModel.getAllCategories()
            homeViewModel.getNonAlcoholicDrinks()
            if(homeScreenState.meals.isEmpty()){
                homeViewModel.getRandomMeal()
            }
        }

        val navigator = LocalNavigator.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = "Hello, Daniel",
                            fontSize = 12.sp
                        )

                        Text(
                            text = "What would you like to cook or drink today?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(70.dp))

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFFB4D1C4))
                            .size(60.dp),
                        contentAlignment = Alignment.Center

                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "Person Profile",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Categories",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                AnimatedVisibility(
                    visible = homeScreenState.isCategoriesLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ){
                    Column(
                        modifier = Modifier.size(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                AnimatedVisibility(visible = homeScreenState.categories.isNotEmpty()){
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(homeScreenState.categories) { category ->
                            CategoriesCard(
                                category = category,
                                onClick = {categoryName ->
                                    navigator?.push(CategoryScreen(categoryName))
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column {
                        Text(
                            text = "Recommendation",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedVisibility(
                            visible = homeScreenState.isRandomLoading,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ){
                            Column(
                                modifier = Modifier.size(100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        AnimatedVisibility(visible = homeScreenState.meals.isNotEmpty()){
                            Row {
                                RandomCard(
                                    meal = homeScreenState.meals[0],
                                    onClick = {mealId ->
                                        navigator?.push(DetailsScreen(mealId = mealId))
                                    }
                                )
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Ordinary Drinks",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        AnimatedVisibility(
                            visible = homeScreenState.isDrinksLoading,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ){
                            Column(
                                modifier = Modifier.size(100.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        AnimatedVisibility(visible = homeScreenState.drinks.isNotEmpty()){
                           LazyRow (
                               horizontalArrangement = Arrangement.spacedBy(8.dp)
                           ){
                               items(homeScreenState.drinks){ drink ->
                                   DrinksCard(
                                       drink = drink,
                                       onClick = { drinkId ->
                                           navigator?.push(DrinkDetailsScreen(drinkId = drinkId))
                                       }
                                   )
                               }
                           }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Rounded.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }
}