package k5.app.ui

sealed interface ResponseState {
    object Empty : ResponseState
    data class Processing(val query: String) : ResponseState
//    data class Success(val matches: k5.app.data.Matches) : ResponseState
    data class Error(val message: String) : ResponseState
}
