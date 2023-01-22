package k5.examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.translate
import k5.K5
import k5.math.cos
import k5.math.map
import k5.math.sin
import k5.math.toRadians
import kotlin.math.sqrt

@ExperimentalGraphicsApi
fun K5.showPhyllotaxis() {
    var n = 0
    val c = 6f
    var start = 0

    show {
        translate(size.width / 2, size.height / 2) {
            for (i in 0 until n) {
                val a = i * 137.5f.toRadians()
                val r = c * sqrt(i.toFloat())
                val hue = map((start + i * 0.5f).sin(), -1f, 1f, 0f, 360f)
                println(hue)
                drawCircle(
                    color = Color.hsv(hue, 1f, 1f),
                    radius = 4f,
                    center = Offset(
                        x = r * a.cos(),
                        y = r * a.sin(),
                    ),
                )
            }
        }
        n += 5
        start += 5
    }
}
