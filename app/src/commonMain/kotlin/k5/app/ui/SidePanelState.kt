package k5.app.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class SidePanelState(val side: Side) {
    private var expandedSize by mutableStateOf(300.dp)
    private var isExpanded by mutableStateOf(true)
    val hint by derivedStateOf { if (isExpanded) "Collapse" else "Expand" }
    val arrow by derivedStateOf { if (isExpanded) side.collapseIcon else side.expandIcon }
    var isResizing by mutableStateOf(false)
    var isResizeEnabled by mutableStateOf(true)
    val size @Composable get() = if (isExpanded) expandedSize else collapsedSize
    val animatedAlpha @Composable get() = animateFloatAsState(if (isExpanded) 1f else 0f, SpringSpec(stiffness = Spring.StiffnessLow)).value
    val animatedSize
        @Composable get() = when {
            isResizing -> size
            else -> animateDpAsState(size, SpringSpec(stiffness = Spring.StiffnessLow)).value
        }

    fun resize(it: Dp) {
        expandedSize = (expandedSize + it).coerceAtLeast(expandedSizeMin)
    }

    fun toggle() {
        isExpanded = !isExpanded
    }
}

private val collapsedSize = 24.dp
private val expandedSizeMin = 90.dp
