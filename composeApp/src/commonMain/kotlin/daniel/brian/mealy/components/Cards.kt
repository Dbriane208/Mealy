package daniel.brian.mealy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.brian.mealy.model.Category
import daniel.brian.mealy.model.Drink
import daniel.brian.mealy.model.Meal
import daniel.brian.mealy.utils.shortenName
import daniel.brian.mealy.utils.shortenNameLength
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CategoriesCard(
    modifier: Modifier = Modifier,
    category: Category,
){
    Card(
        modifier = modifier
            .size(100.dp),
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
                text = category.categoryName.shortenNameLength(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RandomCard(
    modifier: Modifier = Modifier,
    meal: Meal
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .height(200.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ){
        meal.strMealThumb?.let { asyncPainterResource(it) }?.let {
            KamelImage(
                resource = it,
                contentDescription = "Random Image",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun PopularCard(
    modifier: Modifier = Modifier,
    drink: Drink
) {
   Column(
       modifier = modifier,
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {
       Card(
           modifier = modifier
               .size(150.dp),
           elevation = 10.dp,
           shape = RoundedCornerShape(10.dp)
       ){
           Column(
               modifier = modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.SpaceBetween
           ) {
               KamelImage(
                   resource = asyncPainterResource(drink.strDrinkThumb ?: "No Image"),
                   contentDescription = "category image",
                   contentScale = ContentScale.Crop,
                   modifier = Modifier
                       .fillMaxWidth()
               )
           }
       }

       Text(
           text = drink.strDrink?.shortenName() ?: "No name",
           modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 4.dp),
           textAlign = TextAlign.Center
       )
   }

}