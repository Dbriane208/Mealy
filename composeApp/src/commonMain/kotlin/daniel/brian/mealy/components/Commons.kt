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
                val sentences = instructions
                    .replace(Regex("\\d+\\.\\s*"), "")
                    .split(".")
                    .map { it.trim() }
                    .filter { it.isNotBlank() && it.length > 3 }

                sentences.forEachIndexed { index, sentence ->
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${index + 1}. ")
                    }
                    append(sentence.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase()
                        else it.toString()
                    })
                    if (index < sentences.size - 1) {
                        append(".\n")
                    } else {
                        append(".")
                    }
                }
            },
            fontSize = 12.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
