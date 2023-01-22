package k5.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable

@Suppress("unused")
@Composable
fun PanelsLayout(modifier: Modifier = Modifier, vararg sides: SidePanelState, children: @Composable () -> Unit) {
    Layout(children, modifier) { ms, cs ->
        require(ms.size == sides.size + 1)
        val csMin = cs.copy(minWidth = 0)
        var width = cs.maxWidth
        val panels = sides.mapIndexed { i, side -> (side.side == Side.Left) to ms[i].measure(csMin).also { width -= it.width } }
        val cp = ms[sides.size].measure(cs.fixedWidth(width))
        layout(cs.maxWidth, cs.maxHeight) {
            val (lefts, rights) = panels.partition { it.first }
            var x = 0
            val append: (Placeable) -> Unit = { it.place(x, 0); x += it.width }
            lefts.map { it.second }.forEach(append)
            append(cp)
            rights.map { it.second }.forEach(append)
        }
    }
}
