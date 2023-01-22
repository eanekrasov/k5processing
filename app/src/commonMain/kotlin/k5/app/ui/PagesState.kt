package k5.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class PagesState(vararg pages: Page) {
    var active: Page? by mutableStateOf(null)
        private set

    private fun update(value: Page?) {
        active = value
    }

    var pages = mutableStateListOf(*pages)
        private set

    fun activate(page: Page?) = update(pages.firstOrNull { it == page } ?: page?.also(pages::add))
//    fun viewer(file: k5.app.platform.File) = activate(FilePageState(file))
    fun search(query: String = "", scope: CoroutineScope = MainScope()) = activate(SearchPageState(scope, query))
    fun close(state: Page) {
        val index = pages.indexOf(state)
        pages.remove(state)
        if (state == active) update(pages.getOrNull(index.coerceAtMost(pages.lastIndex)))
    }
}
