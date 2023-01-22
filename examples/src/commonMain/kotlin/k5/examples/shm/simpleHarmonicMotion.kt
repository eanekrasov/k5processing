package k5.examples.shm

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import k5.K5
import k5.math.TWO_PI
import k5.math.map
import kotlin.math.sin

fun K5.simulateSineWave() {

    val r = 6
    val total = size.width.toInt() / (r * 2)
    val angles = MutableList(total) {
        map(it.toFloat(), 0f, total.toFloat(), 0f, 2 * TWO_PI.toFloat())
    }

    show {
        translate(500f, 500f) {
            for (i in 0 until angles.size) {
                drawCircle(
                    color = Color.Yellow,
                    radius = r.toFloat(),
                    center = Offset(
                        x = map(i.toFloat(), 0f, angles.size.toFloat(), -400f, 400f),
                        y = map(sin(angles[i]), -1f, 1f, -400f, 400f),
                    ),
                )
                angles[i] += 0.02f
            }
        }
    }
}
