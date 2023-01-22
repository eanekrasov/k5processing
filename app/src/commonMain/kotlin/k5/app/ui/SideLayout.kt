package k5.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import k5.app.ui.fixedWidth

@Composable
fun SideLayout(modifier: Modifier = Modifier, children: @Composable () -> Unit) {
    Layout(children, modifier) { ms, cs ->
        require(ms.size == 2)
        val side = ms[0].measure(cs.copy(minWidth = 0))
        val main = ms[1].measure(cs.fixedWidth(cs.maxWidth - side.width))
        layout(cs.maxWidth, cs.maxHeight) {
            side.place(0, 0)
            main.place(side.width, 0)
        }
    }
}
