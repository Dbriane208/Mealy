package daniel.brian.mealy

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import daniel.brian.mealy.screens.bookmark.BookmarkScreen
import daniel.brian.mealy.screens.home.HomeScreen
import daniel.brian.mealy.screens.profile.ProfileScreen
import daniel.brian.mealy.screens.search.SearchScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        TabNavigator(HomeScreen) {
            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = Color.White,
                        contentColor = Color.White,
                        elevation = 10.dp
                    ) {
                        TabNavigationItem(HomeScreen)
                        TabNavigationItem(BookmarkScreen)
                        TabNavigationItem(SearchScreen)
                        TabNavigationItem(ProfileScreen)
                    }
                }
            ) {
                CurrentTab()
            }
        }
    }
}

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = null) } },
        selectedContentColor = Color.Green,
        unselectedContentColor = Color.Gray
    )

}