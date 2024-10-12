package daniel.brian.mealy.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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