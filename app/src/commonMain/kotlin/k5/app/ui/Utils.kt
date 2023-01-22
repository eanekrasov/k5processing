package k5.app.ui

import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
//import com.arkivanov.decompose.router.stack.StackNavigator
//import com.arkivanov.essenty.lifecycle.LifecycleOwner
//import com.arkivanov.essenty.lifecycle.doOnDestroy
import k5.app.ui.AppTheme.colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

//fun <C : Any> StackNavigator<C>.close(config: C, onComplete: () -> Unit = {}) = navigate({ it - config }, { _, _ -> onComplete() })

//fun <C : Any> StackNavigator<C>.open(config: C, onComplete: () -> Unit = {}) = navigate({ it - config + config }, { _, _ -> onComplete() })

//fun LifecycleOwner.lifecycleScope(context: CoroutineContext = Main) = CoroutineScope(context).apply {
//    lifecycle.doOnDestroy(::cancel)
//}

fun Constraints.fixedWidth(width: Int) = copy(minWidth = width, maxWidth = width)

fun Constraints.unlimitedWidth() = copy(maxWidth = Int.MAX_VALUE)

fun Modifier.unlimitedWidth() = layout { m, cs ->
    with(m.measure(cs.unlimitedWidth())) { layout(cs.maxWidth, height) { place(0, 0) } }
}

fun Modifier.height(fraction: Float) = composed {
    height(with(LocalDensity.current) { LocalSettingsState.current.fontSize.toDp() * fraction })
}

fun Modifier.softWrapLine() = composed {
    val settings = LocalSettingsState.current
    drawWithCache {
        val x = 0.5f * settings.fontSize.toPx() * settings.lineLength
        val a = Offset(x, 0f)
        val b = Offset(x, size.height)
        onDrawBehind { drawLine(colors.backgroundLight, a, b) }
    }
}


fun Modifier.sideBorder(color: Color, side: Side) = drawWithCache {
    val x = when (side) {
        Side.Left -> 0f
        Side.Right -> size.width
    }
    val a = Offset(x, 0f)
    val b = Offset(x, size.height)
    onDrawBehind { drawLine(color, a, b) }
}
