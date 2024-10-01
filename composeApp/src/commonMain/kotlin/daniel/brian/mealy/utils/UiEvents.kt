package daniel.brian.mealy.utils

sealed class UiEvents {
    data class SnackBarEvent(val message: String): UiEvents()
}