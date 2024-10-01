package daniel.brian.mealy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.brian.mealy.model.Category
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CardComponent(
    modifier: Modifier = Modifier,
    category: Category,
){
    Card(
        modifier = modifier
            .size(100.dp)
            .padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            KamelImage(
                resource = asyncPainterResource(category.categoryImg),
                contentDescription = "category image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp)
            )

            Text(
                text = category.categoryName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
