package k5.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import k5.app.ui.AppTheme.colors

@Composable
fun Tab(page: Page, pages: PagesState) {
    Tab(page.title, page == pages.active, page.isCloseable, { pages.close(page) }) { pages.activate(page) }
}

//@Composable
//fun Tab(page: Page, config: CodeViewer.Config, isActive: Boolean, component: CodeViewer) {
//    Tab(page.title, isActive, page.isCloseable, { component.onItemClosed(config) }, { component.onItemClicked(config) })
//}

@Composable
fun Tab(title: String, isActive: Boolean, isCloseable: Boolean, close: () -> Unit, onClick: () -> Unit) {
    Surface(color = if (isActive) colors.backgroundDark else Transparent) {
        val interactions = remember(::MutableInteractionSource)
        Row(Modifier.clickable(interactions, null) { onClick() }.padding(4.dp), verticalAlignment = CenterVertically) {
            Text(title, Modifier.padding(4.dp, 0.dp), LocalContentColor.current, 12.sp)
            Icon(Default.Close, "Close", Modifier.size(24.dp).padding(4.dp), close.takeIf { isCloseable })
        }
    }
}
