package daniel.brian.mealy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import daniel.brian.mealy.screens.bookmark.BookmarkScreen
import daniel.brian.mealy.screens.details.category.CategoryScreen
import daniel.brian.mealy.screens.details.drink.DrinkDetailsScreen
import daniel.brian.mealy.screens.details.meal.DetailsScreen
import daniel.brian.mealy.screens.home.HomeScreen
import daniel.brian.mealy.screens.profile.ProfileScreen
import daniel.brian.mealy.screens.search.SearchScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(MainScreen) { navigator ->
            Scaffold(
                bottomBar = {
                    // showing navigation bar only on main screens
                    val tabScreens = setOf(MainScreen,BookmarkScreen,SearchScreen,ProfileScreen)

                    if (navigator.lastItem in tabScreens) {
                        BottomNavigation(
                            backgroundColor = Color.White,
                            contentColor = Color.White,
                            elevation = 10.dp
                        ) {
                            TabNavigationItem(HomeScreen, navigator)
                            TabNavigationItem(BookmarkScreen, navigator)
                            TabNavigationItem(SearchScreen, navigator)
                            TabNavigationItem(ProfileScreen, navigator)
                        }
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)){
                    CurrentScreen(navigator)
                }
            }
        }
    }
}


@Composable
fun RowScope.TabNavigationItem(tab: Tab, navigator: Navigator) {
    val currentScreen = navigator.lastItem as? MainScreen
    val selected = when (navigator.lastItem) {
        is MainScreen -> currentScreen?.currentTab == tab
        is ProfileScreen -> tab is ProfileScreen
        is BookmarkScreen -> tab is BookmarkScreen
        is SearchScreen -> tab is SearchScreen
        is HomeScreen -> tab is HomeScreen
        else -> navigator.lastItem == tab
    }

    BottomNavigationItem(
        selected = selected,
        onClick = {
            when (tab) {
                is ProfileScreen -> navigator.push(ProfileScreen)
                is BookmarkScreen -> navigator.push(BookmarkScreen)
                is SearchScreen -> navigator.push(SearchScreen)
                is HomeScreen -> navigator.push(MainScreen)
                else -> {
                    if (currentScreen != null) {
                        currentScreen.currentTab = tab
                        navigator.replace(MainScreen)
                    }
                }
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
        is DrinkDetailsScreen -> DrinkDetailsScreen(currentScreen.drinkId).Content()
        is CategoryScreen -> CategoryScreen(currentScreen.categoryName).Content()
        is ProfileScreen -> ProfileScreen.Content()
        is BookmarkScreen -> BookmarkScreen.Content()
        is SearchScreen -> SearchScreen.Content()
    }
}

object MainScreen : Screen {
    var currentTab: Tab = HomeScreen

    @Composable
    override fun Content() {
        currentTab.Content()
    }
}