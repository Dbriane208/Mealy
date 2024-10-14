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
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data class DetailsScreen(
    val mealId : Int
) : Screen{

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val detailsViewModel = getViewModel(Unit, viewModelFactory { DetailsViewModel() })
        val detailsScreenState by detailsViewModel.detailsUiState.collectAsState()

        LaunchedEffect(detailsViewModel){
            detailsViewModel.getMeals(mealId)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when {
                detailsScreenState.isLoading -> {
                    Text("Loading...")
                }
                detailsScreenState.error -> {
                    Text("Error: ${detailsScreenState.errorMessage}")
                }
                detailsScreenState.meal != null -> {
                    val meal = detailsScreenState.meal!!

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .fillMaxHeight(0.25f)
                    ){
                        KamelImage(
                            resource = asyncPainterResource(meal.strMealThumb ?: "No Image"),
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
                                    onClick = {},
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = null,
                                    )
                                }

                            }

                        }
                    }

                    Box(
                        modifier = Modifier
                    ){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = 20.dp,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        ){
                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {

                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ){
                                    TitleText(text = meal.strMeal ?: "Choco Macaroons")

                                    Row(
                                        modifier = Modifier
                                    ){
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
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ){
                                    TitleText(text = "Ingredients")

                                    val ingredients = listOf(
                                        Ingredient(meal.strIngredient1 ?: "", meal.strMeasure1 ?: ""),
                                        Ingredient(meal.strIngredient2 ?: "", meal.strMeasure2 ?: ""),
                                        Ingredient(meal.strIngredient3 ?: "", meal.strMeasure3 ?: ""),
                                        Ingredient(meal.strIngredient4 ?: "", meal.strMeasure4 ?: ""),
                                        Ingredient(meal.strIngredient5 ?: "", meal.strMeasure5 ?: ""),
                                        Ingredient(meal.strIngredient6 ?: "", meal.strMeasure6 ?: ""),
                                        Ingredient(meal.strIngredient7 ?: "", meal.strMeasure7 ?: ""),
                                        Ingredient(meal.strIngredient8 ?: "", meal.strMeasure8 ?: ""),
                                        Ingredient(meal.strIngredient9 ?: "", meal.strMeasure9 ?: ""),
                                        Ingredient(meal.strIngredient10 ?: "", meal.strMeasure10 ?: ""),
                                        Ingredient(meal.strIngredient11 ?: "", meal.strMeasure11 ?: "")
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
//        Row(
//            modifier = Modifier
//        ){
//            ExtendedFloatingActionButton(
//                modifier = Modifier.align(Alignment.CenterVertically),
//                onClick = {},
//                icon = { Icon(Icons.Filled.PlayArrow, "Play Arrow") },
//                text = { Text(
//                    text = "Watch Video",
//                    color = Color.White
//                )
//                },
//                elevation = FloatingActionButtonDefaults.elevation(10.dp),
//                backgroundColor = Color(0xFF0C9A61)
//            )
//        }
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
}