package k5.app.ui

sealed interface Page {
    val title: String
    val isCloseable: Boolean
}
