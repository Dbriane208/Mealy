package daniel.brian.mealy.screens.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.imageResource

object BookmarkScreen : Tab {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color(0xf7f7f7)),
            contentAlignment = Alignment.Center
        ){
           Text("Bookmark screen")
        }
    }

    override val options: TabOptions
       @Composable
       get(){
           val icon = rememberVectorPainter(Icons.Outlined.Favorite)
          return remember {
              TabOptions(
                  index = 2u,
                  title = "Bookmark",
                  icon = icon
              )
          }
       }
}