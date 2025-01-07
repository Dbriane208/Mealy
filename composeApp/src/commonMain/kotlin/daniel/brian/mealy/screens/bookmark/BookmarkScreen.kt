package daniel.brian.mealy.screens.bookmark

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import daniel.brian.mealy.components.SavedDrinkCard
import daniel.brian.mealy.components.SavedMealCard
import daniel.brian.mealy.database.DrinkDao
import daniel.brian.mealy.database.MealDao
import daniel.brian.mealy.database.MealDatabase
import daniel.brian.mealy.screens.details.drink.DrinkDetailsScreen
import daniel.brian.mealy.screens.details.meal.DetailsScreen
import daniel.brian.mealy.utils.DrinksCardShimmerEffect
import kotlinx.coroutines.launch

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
        val scope = rememberCoroutineScope()

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
                // Loading state
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
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(100.dp)
                            ) {
                                DrinksCardShimmerEffect(modifier = Modifier)
                            }
                        }
                    }
                }

                if(meals.isEmpty() && drinks.isEmpty()){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No saved Items")
                    }
                }

                AnimatedVisibility(visible = !bookMarkScreenState.isLoading) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp),
                    ) {
                        if (meals.isNotEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Saved Meals",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            items(meals) { meal ->
                                val offsetX = remember { Animatable(0f) }
                                val swipeThreshold = 200f

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .pointerInput(Unit) {
                                            detectHorizontalDragGestures(
                                                onDragEnd = {
                                                    scope.launch {
                                                        if (offsetX.value > swipeThreshold || offsetX.value < -swipeThreshold) {
                                                            mealDao.delete(meal = meal)
                                                        } else {
                                                            offsetX.animateTo(0f)
                                                        }
                                                    }
                                                },
                                                onHorizontalDrag = { _, dragAmount ->
                                                    scope.launch {
                                                        offsetX.snapTo(offsetX.value + dragAmount)
                                                    }
                                                }
                                            )
                                        }
                                        .offset { IntOffset(offsetX.value.toInt(), 0) }
                                ){
                                    SavedMealCard(
                                        meal = meal,
                                        onClick = { item ->
                                            navigator?.push(DetailsScreen(item, mealDao))
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        if (drinks.isNotEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Saved Drinks",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            items(drinks) { drink ->
                                val offsetX = remember { Animatable(0f) }
                                val swipeThreshold = 200f

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .pointerInput(Unit) {
                                            detectHorizontalDragGestures(
                                                onDragEnd = {
                                                    scope.launch {
                                                        if (offsetX.value > swipeThreshold || offsetX.value < -swipeThreshold) {
                                                            drinkDao.deleteDrink(drink = drink)
                                                        } else {
                                                            offsetX.animateTo(0f)
                                                        }
                                                    }
                                                },
                                                onHorizontalDrag = { _, dragAmount ->
                                                    scope.launch {
                                                        offsetX.snapTo(offsetX.value + dragAmount)
                                                    }
                                                }
                                            )
                                        }
                                        .offset { IntOffset(offsetX.value.toInt(), 0) }
                                ){
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