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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import daniel.brian.mealy.screens.bookmark.BookmarkScreen
import daniel.brian.mealy.screens.details.DetailsScreen
import daniel.brian.mealy.screens.home.HomeScreen
import daniel.brian.mealy.screens.profile.ProfileScreen
import daniel.brian.mealy.screens.search.SearchScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    MaterialTheme {

        Navigator(MainScreen) { navigator ->
            Scaffold(
                bottomBar = {
                    if(navigator.lastItem is MainScreen){
                        BottomNavigation(
                            backgroundColor = Color.White,
                            contentColor = Color.White,
                            elevation = 10.dp
                        ) {
                            TabNavigationItem(HomeScreen,navigator)
                            TabNavigationItem(BookmarkScreen,navigator)
                            TabNavigationItem(SearchScreen,navigator)
                            TabNavigationItem(ProfileScreen,navigator)
                        }
                    }
                }
            ) {
                CurrentScreen(navigator)
            }
        }
    }
}

@Composable
fun RowScope.TabNavigationItem(tab: Tab, navigator: Navigator) {
    val currentScreen = navigator.lastItem as? MainScreen
    val selected = currentScreen?.currentTab == tab

    BottomNavigationItem(
        selected = selected,
        onClick = {
            if(currentScreen != null) {
                currentScreen.currentTab = tab
                navigator.replace(MainScreen)
            }
        },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = null) } },
        selectedContentColor = Color.Green,
        unselectedContentColor = Color.Gray
    )
}

@Composable
fun CurrentScreen(navigator: Navigator) {
    when (val currentScreen = navigator.lastItem) {
        is MainScreen -> currentScreen.currentTab.Content()
        is DetailsScreen -> DetailsScreen(currentScreen.mealId).Content()
        // Add other screens here if needed
    }
}

object MainScreen : Screen {
    var currentTab: Tab = HomeScreen

    @Composable
    override fun Content() {
        currentTab.Content()
    }

}