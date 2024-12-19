package daniel.brian.mealy.screens.details.drink

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import daniel.brian.mealy.components.IconTextPair
import daniel.brian.mealy.components.NumberedInstructions
import daniel.brian.mealy.components.TitleText
import daniel.brian.mealy.database.DrinkDao
import daniel.brian.mealy.utils.DetailsScreenShimmerEffect
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class DrinkDetailsScreen(
    val drinkId: Int,
    val drinkDao: DrinkDao
) : Screen {

    @Composable
    override fun Content() {
        val drinkDetailsViewModel = getViewModel(Unit, viewModelFactory { DrinkDetailsViewModel() })
        val drinkDetailsState by drinkDetailsViewModel.drinkUiState.collectAsState()
        var isClicked by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()

        LaunchedEffect(drinkId) {
            drinkDetailsViewModel.getDrinks(drinkId)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when {
                drinkDetailsState.isLoading -> {
                    DetailsScreenShimmerEffect(modifier = Modifier)
                }

                drinkDetailsState.error -> {
                    Text("Error: ${drinkDetailsState.errorMessage}")
                }

                drinkDetailsState.drink != null -> {
                    val drink = drinkDetailsState.drink!!

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(300.dp)
                    ) {
                        KamelImage(
                            resource = asyncPainterResource(drink.strDrinkThumb ?: "No Image"),
                            contentDescription = "Meal Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .align(alignment = Alignment.TopStart)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .size(30.dp)
                            ) {
                                IconButton(
                                    modifier = Modifier,
                                    onClick = {
                                        navigator?.pop()
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                    )
                                }
                            }


                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .size(30.dp)
                            ) {
                                IconButton(
                                    modifier = Modifier,
                                    onClick = {
                                        isClicked = !isClicked
                                        scope.launch {
                                            try {
                                                drinkDetailsState.drink?.let { drink ->
                                                    drinkDao.upsertDrink(drink)
                                                    val drinks = drinkDao.getAllDrinks().first()
                                                    if (drinks.contains(drink)) {
                                                        Napier.d("Drink upsert successful in database: $drink")
                                                    } else {
                                                        Napier.e("Meal upsert failed")
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                Napier.e("Drink Exception $e")
                                            }
                                        }
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = null,
                                        tint = if (isClicked) Color.Red else Color.Gray
                                    )
                                }

                            }

                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(700.dp)
                            .offset(y = (-10).dp),
                        elevation = 20.dp,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {

                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                TitleText(text = drink.strDrink ?: "Choco Macaroons")

                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    IconTextPair(
                                        icon = Icons.Outlined.Star,
                                        contentDescription = "Category",
                                        text = drink.strCategory ?: "Category null"
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    IconTextPair(
                                        icon = Icons.Outlined.Info,
                                        contentDescription = "Info null",
                                        text = "More Info"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            NumberedInstructions(drink.strInstructions ?: "Instructions null")

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                TitleText(text = "Ingredients")

                                val ingredients = listOf(
                                    Ingredient(drink.strIngredient1 ?: "", drink.strMeasure1 ?: ""),
                                    Ingredient(drink.strIngredient2 ?: "", drink.strMeasure2 ?: ""),
                                    Ingredient(drink.strIngredient3 ?: "", drink.strMeasure3 ?: ""),
                                    Ingredient(drink.strIngredient4 ?: "", drink.strMeasure4 ?: ""),
                                    Ingredient(drink.strIngredient5 ?: "", drink.strMeasure5 ?: ""),
                                    Ingredient(drink.strIngredient6 ?: "", drink.strMeasure6 ?: ""),
                                    Ingredient(drink.strIngredient7 ?: "", drink.strMeasure7 ?: ""),
                                    Ingredient(drink.strIngredient8 ?: "", drink.strMeasure8 ?: ""),
                                    Ingredient(drink.strIngredient9 ?: "", drink.strMeasure9 ?: ""),
                                    Ingredient(
                                        drink.strIngredient10 ?: "",
                                        drink.strMeasure10 ?: ""
                                    ),
                                    Ingredient(
                                        drink.strIngredient11 ?: "",
                                        drink.strMeasure11 ?: ""
                                    )
                                ).filter { it.name.isNotBlank() && it.measure.isNotBlank() }

                                ingredients.forEach { ingredient ->
                                    IngredientRow(ingredient = ingredient)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    data class Ingredient(
        val name: String,
        val measure: String
    )

    @Composable
    fun IngredientRow(
        modifier: Modifier = Modifier,
        ingredient: Ingredient
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.weight(1f),
                text = ingredient.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = ingredient.measure,
                fontSize = 14.sp
            )
        }
    }

}