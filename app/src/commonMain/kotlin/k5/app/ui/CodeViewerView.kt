package k5.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CodeViewerView(state: CodeViewerState) = Surface {
    SideLayout(Modifier.fillMaxSize()) {
        //SidePanelView("Root", state.leftSide) { ScrollColumn(Modifier.fillMaxSize()) { items(state.tree.toItems()) { item -> TreeItemView(item) } } }
        Column(Modifier.fillMaxSize()) {
            LazyRow {
                items(state.pages.pages) { page ->
                    Tab(page, state.pages)
                }
            }
            PageView(state.pages, Modifier.weight(1f))
            StatusBar()
        }
    }
}
