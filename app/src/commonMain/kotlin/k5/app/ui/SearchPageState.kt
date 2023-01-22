package k5.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import k5.app.ui.ResponseState.Empty
import k5.app.ui.ResponseState.Processing
import kotlinx.coroutines.CoroutineScope

class SearchPageState(private val scope: CoroutineScope, initial: String = "") : Page {
    var state by mutableStateOf<ResponseState>(Empty)
    var query by mutableStateOf(initial)
    val onQueryChanged: (String) -> Unit = { query = it }
    val onSearchRequested: () -> Unit = { state = Processing(query) }
    override val title = query
    override val isCloseable = false
    override fun equals(other: Any?) = this === other || other is SearchPage
    override fun hashCode() = "SearchPage".hashCode()
//    fun downloadJar(item: Match) = scope.launch {
//        val name = "${item.name}-${item.version}-sources.jar"
//
//    }
}
