package k5.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import k5.app.ui.AppTheme.colors

@Composable
fun PageView(pages: PagesState, modifier: Modifier = Modifier) {
    Box(modifier) {
        Surface(Modifier.fillMaxSize(), color = colors.backgroundDark) {
            val page = pages.active
            key(page) {
                when (page) {
//                    is SearchPageState -> SearchPageView(page)
                    //is FilePageState -> FilePageView(page)
                    else -> EmptyView(pages)
                }
            }
        }
    }
}
