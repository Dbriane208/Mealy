package daniel.brian.mealy.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
