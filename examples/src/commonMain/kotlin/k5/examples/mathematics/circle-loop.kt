package k5.examples.mathematics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import k5.K5
import k5.math.AngleMode
import k5.math.PI
import k5.math.angleMode
import kotlin.math.cos
import kotlin.math.sin

fun K5.showCircleLoop() {

    angleMode = AngleMode.DEGREES
    noLoop()
    val radius = 100f

    show {
        var theta = 0.0
        var r = radius.toDouble()
        for (i in 0..1000) {
            translate(size.width / 2 - r.toFloat() / 2, size.height / 2 - r.toFloat() / 2) {
                val x = radius * cos(Math.toRadians(theta))
                val y = radius * sin(Math.toRadians(theta * 2))
                drawOval(
                    color = Color(0x1EFFA500),
                    topLeft = Offset(x.toFloat(), y.toFloat()),
                    size = Size(r.toFloat(), r.toFloat()),
                    style = Stroke(width = 1.0f),
                )
            }
            r += cos(theta) * sin(theta / 2) + sin(theta) * cos(theta / 2)
            theta += PI / 2
        }
    }
}
