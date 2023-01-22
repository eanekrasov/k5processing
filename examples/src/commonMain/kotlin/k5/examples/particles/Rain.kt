package k5.examples.particles

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5.K5
import k5.math.PI
import k5.math.TAU
import kotlin.math.tan
import kotlin.random.Random

fun K5.showRain() {

    val N = 200
    var t = 0f
    val r = List(N) { Random.nextFloat() }

    show(modifier = Modifier.background(Color(0xFFE8FFFF))) {

        t += 0.002f
        for (i in 0 until N) {
            val x = size.width * r[(i + 2) % N]
            val y = (tan(-PI / 2 + TAU * (t + r[i])) * 10 + size.width * (1 - r[(i + 1) % N])).toFloat()
            val radius = 10 * r[(i + 3) % N]
            drawCircle(
                Color(0xFF3498DB),
                center = Offset(x, y),
                radius = radius,
                alpha = 0.7f
            )
        }
    }
}
