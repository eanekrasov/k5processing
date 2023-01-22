package k5.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import k5.app.platform.cursorForHorizontalResize

@Composable
internal fun SidePanelView(
    title: String = "Files",
    state: SidePanelState,
    content: @Composable () -> Unit
) = Box(Modifier.fillMaxHeight().width(state.animatedSize)) {
    Box(Modifier.fillMaxSize().graphicsLayer(state.animatedAlpha)) {
        Column {
            Text(title, Modifier.padding(12.dp, 8.dp).fillMaxWidth(), fontSize = 12.sp, textAlign = TextAlign.Center)
            Surface(Modifier.fillMaxSize()) {
                content()
            }
        }
    }
    Icon(
        state.arrow,
        state.hint,
        Modifier.padding(top = 4.dp).width(24.dp).clickable { state.toggle() }.padding(4.dp).align(state.side.alignment),
        LocalContentColor.current
    )
    val density = LocalDensity.current
    Splitter(state, Modifier.align(state.side.alignment))
    Box(Modifier.align(state.side.alignment).width(8.dp).fillMaxHeight().sideBorder(AppTheme.colors.backgroundDark, state.side)
        .draggable(
            state = rememberDraggableState { state.resize(density.run { if (state.side == Side.Left) it.toDp() else -it.toDp() }) },
            orientation = Orientation.Horizontal,
            enabled = state.isResizeEnabled,
            startDragImmediately = true,
            onDragStarted = { state.isResizing = true },
            onDragStopped = { state.isResizing = false }
        ).cursorForHorizontalResize())
}
