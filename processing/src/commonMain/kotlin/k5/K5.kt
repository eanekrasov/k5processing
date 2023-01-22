package k5

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import k5.ui.Sketch
import k5.ui.SketchView

/**
 * Builder for the K5. All the params passed are applied to a [Window] component.
 *
 * @param size The size of window is taken in floats in order to perform canvas related operations easily on floats which includes the dimension of window.
 * @param title The title of the window is displayed in the native border.
 * @param size The initial size of the window.
 * @param icon The icon for the window displayed on the system taskbar.
 * @param undecorated Removes the native window border if set to true. The default is false.
 * @param resizable Makes the window resizable if is set to true. The default is true.
 * @param focusable Makes the window focusable.
 * @param alwaysOnTop Should window always be on top of others.
 * @param onPreviewKeyEvent Invoked when the user interacts with the hardware keyboard. Gives a focused component`s ancestors chance to intercept a [KeyEvent].
 * Return true to stop propagation of this event to children. The unconsumed event will be sent back up to the root using the onKeyEvent callback.
 * @param onKeyEvent Invoked when the user interacts with the hardware keyboard. Return true to stop propagation of this event.
 * If you return false, the key event will be sent to this [onKeyEvent]'s parent.
 */
fun k5(
    size: Size = Size(1000f, 1000f),
    title: String = "K5 Compose Playground",
    icon: Painter? = null,
    undecorated: Boolean = false,
    resizable: Boolean = false,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: KeyEventHandler = { false },
    onKeyEvent: KeyEventHandler = { false },
    block: K5.() -> Unit
) = K5Impl(
    size = size,
    title = title,
    icon = icon,
    undecorated = undecorated,
    resizable = resizable,
    enabled = enabled,
    focusable = focusable,
    alwaysOnTop = alwaysOnTop,
    onPreviewKeyEvent = onPreviewKeyEvent,
    onKeyEvent = onKeyEvent,
).block()

typealias KeyEventHandler = (KeyEvent) -> Boolean

typealias Controls = @Composable ControlsScope.() -> Unit

interface K5 {
    /**
     * Use this property to get the actual [k5] Playground size in Floats. Subtracting the 56f - which is the toolbar height of the window.
     * When the size of the window is set with `size` param in [k5] builder, it's applied to window and when
     * the canvas is rendered in the window with [Modifier.fillMaxSize] it takes whole window except the toolbar.
     *
     * TODO: Fix the dimensions for a given k5 playground considering density
     */
    val size: Size

    /**
     * Call method to stop the looping of canvas. You can also call it to freeze the time frame for a canvas
     */
    fun noLoop()

    /**
     * Shows the canvas window and renders it for each frame repetitively.
     * Internally, this starts the Jetpack Compose Window and renders the [sketch] requested by user into the Jetpack Compose
     * [Canvas] Composable. The size of the [Canvas] will be same as the [size] passed in [k5] method by default.
     * One can change the canvas size and window size with the help of modifiers.
     * In order to keep the animation running (rendering canvas continuously), it requests to run the frame of animation in nanos.
     * All the [modifier] will be applied to the [Canvas].
     *
     * @param modifier Jetpack compose [Modifier]
     * @param sketch drawScope - Compose canvas drawscope
     */
    fun show(
        modifier: Modifier = Modifier.fillMaxSize(),
        sketch: Sketch,
    )

    /**
     * Shows canvas window as well as controls view side by side.
     * Internally, this starts the Jetpack Compose Window and renders the [sketch] requested by user into the Jetpack Compose
     * [Canvas] Composable. The size of the [Canvas] will be same as the [size] passed in [k5] method by default.
     * One can change the canvas size and window size with the help of modifiers.
     * In order to keep the animation running (rendering canvas continuously), it requests to run the frame of animation in nanos.
     *
     * Also, you can add your controls like sliders, checkboxes, radio-buttons, pickers etc as a control to change/provide
     * inputs to your sketch. This is just simple [Composable] function, so you can add all the [Composable] elements you want.
     * The controls are shown on the right side in the window. You can use Compose States to store/change your input and use it in your sketch.
     */
    fun showWithControls(
        modifier: Modifier = Modifier.background(Transparent),
        controls: Controls? = null,
        sketch: Sketch,
    )
    fun sketch(
        sketch: Sketch,
    )
}

class K5Impl(
    override val size: Size = Size(1000f, 1000f), // Size(size.width, size.height - HEADER_HEIGHT = 56f)
    private val title: String = "K5 Compose Playground",
    private val icon: Painter? = null,
    private val undecorated: Boolean = false,
    private val resizable: Boolean = false,
    private val enabled: Boolean = true,
    private val focusable: Boolean = true,
    private val alwaysOnTop: Boolean = false,
    private val onPreviewKeyEvent: KeyEventHandler = { false },
    private val onKeyEvent: KeyEventHandler = { false },
) : K5 {

    private var stopLoop = false
    val scope = ControlsScope()
    override fun noLoop() {
        stopLoop = true
    }
    override fun sketch(sketch: Sketch) {
        scope.sketch(sketch)
    }

    override fun show(modifier: Modifier, sketch: Sketch) = render(modifier, null, sketch)

    override fun showWithControls(modifier: Modifier, controls: Controls?, sketch: Sketch) = render(modifier, controls, sketch)

    /**
     * Starts the Jetpack Compose Window and renders the [sketch] requested by user into the Jetpack Compose [Canvas] Composable.
     *
     * @param modifier - Jetpack compose [Modifier]. Will be applied to the [Canvas]
     * @param sketch - The content to be drawn on to [Canvas]
     */
    private fun render(modifier: Modifier, controls: Controls?, sketch: Sketch) {
        sketch(sketch)
        application {
            val (width, height) = with(LocalDensity.current) {
                Pair(size.width.toDp(), size.height.toDp())
            }
            val windowState = rememberWindowState(
                width = if (controls != null) width * 5 / 3 else width,
                height = height,
            )
            Window(
                onCloseRequest = ::exitApplication,
                state = windowState,
                title = title,
                icon = icon,
                undecorated = undecorated,
                resizable = resizable,
                enabled = enabled,
                focusable = focusable,
                alwaysOnTop = alwaysOnTop,
                onPreviewKeyEvent = onPreviewKeyEvent,
                onKeyEvent = onKeyEvent,
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.width(width).height(height).clipToBounds()) {
                        SketchView(modifier, stopLoop, scope.sketchState)
                    }
                    Box(modifier = Modifier.height(height).background(Color.LightGray).padding(16.dp)) {
                        Column {
                        controls?.invoke(scope)
                        }
                    }
                }
            }
        }
    }
}

class ControlsScope(sketch: Sketch = {}) {
    var sketchState by mutableStateOf(sketch)
    fun sketch(sketch: Sketch) {
        sketchState = sketch
    }
}