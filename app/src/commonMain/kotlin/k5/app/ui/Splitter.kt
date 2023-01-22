package k5.app.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import k5.app.platform.cursorForHorizontalResize

@Composable
fun Splitter(state: SidePanelState, modifier: Modifier = Modifier, color: Color = AppTheme.colors.backgroundDark) {
    val density = LocalDensity.current
    Box(modifier.width(8.dp).fillMaxHeight().sideBorder(color, state.side).draggable(
        state = rememberDraggableState { state.resize(density.run { if (state.side == Side.Left) it.toDp() else -it.toDp() }) },
        orientation = Orientation.Horizontal,
        enabled = state.isResizeEnabled,
        startDragImmediately = true,
        onDragStarted = { state.isResizing = true },
        onDragStopped = { state.isResizing = false }
    ).cursorForHorizontalResize())
}
