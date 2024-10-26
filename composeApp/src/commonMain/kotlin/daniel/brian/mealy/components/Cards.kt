package daniel.brian.mealy.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.brian.mealy.model.remote.Category
import daniel.brian.mealy.model.remote.CategoryDetails
import daniel.brian.mealy.model.remote.Drink
import daniel.brian.mealy.model.remote.Meal
import daniel.brian.mealy.utils.shortenName
import daniel.brian.mealy.utils.shortenNameLength
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CategoriesCard(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: (String) -> Unit
){
    Card(
        modifier = modifier
            .size(100.dp)
            .clickable { onClick(category.categoryName) },
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
    meal: Meal,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .height(200.dp)
            .clickable { meal.idMeal?.toIntOrNull()?.let { onClick(it)}},
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
fun DrinksCard(
    modifier: Modifier = Modifier,
    drink: Drink,
    onClick: (Int) -> Unit
) {
   Column(
       modifier = modifier,
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {
       Card(
           modifier = modifier
               .size(150.dp)
               .clickable { drink.idDrink?.toIntOrNull()?.let { onClick(it) }  },
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