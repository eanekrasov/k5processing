@file:Suppress("UNUSED_VARIABLE")

package k5.ui

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Move
import androidx.compose.ui.input.pointer.onPointerEvent

typealias Sketch = DrawScope.() -> Unit

/**
 * Run frame time with nanoseconds
 * @param dt - Change it time
 * @param previousTime - previous time to calculate change in time
 */
@Composable
private fun requestAnimationFrame(dt: MutableState<Float>, previousTime: MutableState<Long>) {
    LaunchedEffect(Unit) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                dt.value = ((it - previousTime.value) / 1E7).toFloat()
                previousTime.value = it
            }
        }
    }
}

/**
 * Simple canvas view to render the sketch
 */
@Composable
internal fun SketchView(modifier: Modifier, stopLoop: Boolean, sketch: Sketch) {
    // dt = change in time
    val dt = remember { mutableStateOf(0f) }
    // TODO : Show elapsed time and frames per second on toolbar of window
    var startTime = remember { mutableStateOf(0L) }
    val previousTime = remember { mutableStateOf(System.nanoTime()) }
    Canvas(modifier = Modifier.fillMaxSize().background(Color.Black).then(modifier)) {
        val stepFrame = dt.value
        sketch(this)
    }

    if (!stopLoop) {
        requestAnimationFrame(dt, previousTime)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onPointerMove(block: (Float, Float) -> Unit) = onPointerEvent(Move) { it.changes.first().position.run { block(x, y) } }