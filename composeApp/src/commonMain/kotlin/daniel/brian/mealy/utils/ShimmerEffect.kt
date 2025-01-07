package daniel.brian.mealy.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.brian.mealy.components.IconTextPair
import daniel.brian.mealy.components.NumberedInstructions
import daniel.brian.mealy.components.TitleText
import daniel.brian.mealy.screens.details.drink.DrinkDetailsScreen.Ingredient
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    ).value

    background(color = Color(0xFFC3C3C3).copy(alpha = alpha))
}

@Composable
fun CategoriesCardShimmerEffect(
    modifier: Modifier = Modifier,
){
    Card(
        modifier = modifier.shimmerEffect(),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            KamelImage(
                resource = asyncPainterResource(""),
                contentDescription = "category image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmerEffect()
            )
        }
    }
}

@Composable
fun RandomCardShimmerEffect(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .height(200.dp)
            .shimmerEffect(),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        KamelImage(
            modifier = modifier.shimmerEffect(),
            resource = asyncPainterResource(""),
            contentDescription = "Random Image",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun DrinksCardShimmerEffect(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.shimmerEffect(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = modifier
                .size(150.dp)
                .shimmerEffect(),
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ){
            Column(
                modifier = modifier.fillMaxSize().shimmerEffect(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                KamelImage(
                    resource = asyncPainterResource(""),
                    contentDescription = "category image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect()
                )
            }
        }

        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .shimmerEffect(),
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun DetailsScreenShimmerEffect(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .shimmerEffect()
                        .clip(CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .shimmerEffect()
                        .clip(CircleShape)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .offset(y = (-20).dp),
            elevation = 20.dp,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(20.dp)
                                .shimmerEffect()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(24.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))

                repeat(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(24.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))

                repeat(6) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(16.dp)
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(16.dp)
                                .shimmerEffect()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun SearchCardShimmerEffect(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = modifier
                .size(150.dp)
                .shimmerEffect(),
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ){
            KamelImage(
                resource = asyncPainterResource(""),
                contentDescription = "category image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .shimmerEffect()
            )
        }

        Text(
            text = "No Meal with that Name. Try Again!!",
            modifier = Modifier
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Center
        )
    }

}