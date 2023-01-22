@file:Suppress("unused")

package k5.examples.mathematics

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5.K5
import k5.math.map
import k5.ui.onPointerMove
import kotlin.math.cos
import kotlin.math.sin

fun K5.showRibbon() {

    var r = 50f
    var t = 0.0
    var dt = 0.0001

    noLoop()

    show(modifier = Modifier.background(Color(0xFFE6E6FA)).onPointerMove { x, _ ->
        r = map(x, 0f, size.width, 10f, 100f)
    }) {
        for (i in 0 until 150000) {
            val delta = 2.0 * r * cos(4.0 * dt * t) + r * cos(1.0 * t)
            val blue = 2.0 * r * sin(t) - r * cos(3.0 * dt * t)
            val color = Color(delta.toInt(), blue.toInt(), 100, 10)

            val x = 2.0 * r * sin(2.0 * dt * t) + r * cos(1.0 * t * dt) + size.width / 2
            val y = 2.0 * r * sin(1.0 * dt * t) - r * sin(5.0 * t) + size.height / 2
            drawCircle(color, 1.0f, Offset(x.toFloat(), y.toFloat()))

            t += 0.01
            dt += 0.1
        }
    }
}
