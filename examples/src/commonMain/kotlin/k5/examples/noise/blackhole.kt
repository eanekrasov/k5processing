package k5.examples.noise

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5.K5
import k5.math.AngleMode
import k5.math.angleMode
import k5.math.cos
import k5.math.noise3D
import k5.math.random
import k5.math.sin
import kotlin.math.pow
import kotlin.math.sqrt

fun K5.showBlackHole() {

    noLoop()
    angleMode = AngleMode.DEGREES

    val circleNumbers = 200
    val circleGap = 0.01f
    var i = 0
    show {
        while (i <= circleNumbers) {
            val radius = (size.width / 10) + i * 0.05f
            val k = (0.5f..1f).random() * sqrt(1.0 * i / circleNumbers).toFloat()
            val noisiness = 400f * (1.0f * i / circleNumbers).pow(2)

            var theta = 0f
            var bx = 0f
            var by = 0f
            while (theta < 361f) {
                val r1 = theta.cos()
                val r2 = theta.sin()
                val r = radius + noise3D(1.0 * k * r1, 1.0 * k * r2, 1.0 * i * circleGap).toFloat() * noisiness

                val x = size.width / 2 + r * theta.cos()
                val y = size.height / 2 + r * theta.sin()

                if (bx == 0f && by == 0f) {
                    bx = x
                    by = y
                }

                drawLine(color = Color(0xfff8a614), start = Offset(bx, by), end = Offset(x, y), 0.4f)
                bx = x
                by = y
                theta += 1.0f
            }
            i++
        }
    }
}
