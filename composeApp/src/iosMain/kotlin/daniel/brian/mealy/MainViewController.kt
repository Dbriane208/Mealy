package daniel.brian.mealy

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App(dao, drinkDao) }