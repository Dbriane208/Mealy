package daniel.brian.mealy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import daniel.brian.mealy.model.remote.CategoryDetails
import daniel.brian.mealy.utils.shortenName
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun IconTextPair(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    contentDescription: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            fontSize = 12.sp
        )
    }
}

@Composable
fun NumberedInstructions(instructions: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TitleText(text = "Instructions")

        Text(
            text = buildAnnotatedString {
                val sentences = instructions.split(". ").filter { it.isNotBlank() }
                sentences.forEachIndexed { index, sentence ->
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${index + 1}. ")
                    }
                    append("$sentence.")
                    if (index < sentences.size - 1) {
                        append("\n\n")
                    }
                }
            },
            fontSize = 12.sp,
        )
    }
}

@Composable
fun CategoryCard(
    modifier: Modifier,
    category: CategoryDetails
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 10.dp
        ){
            KamelImage(
                resource = asyncPainterResource(category.strMealThumb ?: "No Image"),
                contentDescription = "category image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            text = category.strMeal?.shortenName() ?: "Null",
            textAlign = TextAlign.Center
        )

    }
}
