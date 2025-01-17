package daniel.brian.mealy.screens.details.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.outlined.LocationOn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import daniel.brian.mealy.components.IconTextPair
import daniel.brian.mealy.components.NumberedInstructions
import daniel.brian.mealy.components.TitleText
import daniel.brian.mealy.database.MealDao
import daniel.brian.mealy.model.remote.Meal
import daniel.brian.mealy.utils.DetailsScreenShimmerEffect
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class DetailsScreen(
    val mealId : Int,
    val mealDao: MealDao
) : Screen{

    @Composable
    override fun Content() {
        val detailsViewModel = getViewModel(Unit, viewModelFactory { DetailsViewModel() })
        val detailsScreenState by detailsViewModel.detailsUiState.collectAsState()
        val navigator = LocalNavigator.current
        var isClicked by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(mealId) {
            detailsViewModel.getMeals(mealId)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when {
                detailsScreenState.isLoading -> {
                    DetailsScreenShimmerEffect(modifier = Modifier)
                }

                detailsScreenState.error -> {
                    Text("Error: ${detailsScreenState.errorMessage}")
                }

                detailsScreenState.meal != null -> {
                    val meal = detailsScreenState.meal!!

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(370.dp)
                    ) {
                        KamelImage(
                            resource = asyncPainterResource(meal.strMealThumb ?: "No Image"),
                            contentDescription = "Meal Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .align(alignment = Alignment.TopStart)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CircleIconButton(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.Black,
                                onClick = {
                                navigator?.pop()
                            })

                            CircleIconButton(
                                Icons.Filled.Favorite,
                                tint = if (isClicked) Color.Red else Color.Gray,
                                onClick = {
                                    isClicked = !isClicked
                                    scope.launch {
                                    try {
                                        detailsScreenState.meal?.let { meal ->
                                            mealDao.upsert(meal)
                                        }
                                    } catch (e: Exception) {
                                        Napier.e("Meal Exception $e ")
                                    }
                              }
                            })

                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .offset(y = (-10).dp)
                        ,
                        elevation = 20.dp,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp).fillMaxHeight()
                        ) {

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                TitleText(text = meal.strMeal ?: "Choco Macaroons")

                                Row(
                                    modifier = Modifier
                                ) {
                                    IconTextPair(
                                        icon = Icons.Outlined.Star,
                                        contentDescription = "Category",
                                        text = meal.strCategory ?: "Category null"
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    IconTextPair(
                                        icon = Icons.Outlined.LocationOn,
                                        contentDescription = "Area",
                                        text = meal.strArea ?: "Area null"
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

                            NumberedInstructions(meal.strInstructions ?: "Instructions null")

                            Spacer(modifier = Modifier.height(10.dp))

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                TitleText(text = "Ingredients")

                                val ingredients = listOf(
                                    Ingredient(
                                        meal.strIngredient1 ?: "",
                                        meal.strMeasure1 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient2 ?: "",
                                        meal.strMeasure2 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient3 ?: "",
                                        meal.strMeasure3 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient4 ?: "",
                                        meal.strMeasure4 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient5 ?: "",
                                        meal.strMeasure5 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient6 ?: "",
                                        meal.strMeasure6 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient7 ?: "",
                                        meal.strMeasure7 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient8 ?: "",
                                        meal.strMeasure8 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient9 ?: "",
                                        meal.strMeasure9 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient10 ?: "",
                                        meal.strMeasure10 ?: ""
                                    ),
                                    Ingredient(
                                        meal.strIngredient11 ?: "",
                                        meal.strMeasure11 ?: ""
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
        ingredient : Ingredient
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
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

    @Composable
    fun CircleIconButton(icon: ImageVector, tint: Color, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(30.dp)
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = icon,
                    tint = tint,
                    contentDescription = null,
                )
            }
        }
    }

}