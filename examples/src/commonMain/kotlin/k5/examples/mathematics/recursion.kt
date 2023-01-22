package k5.examples.mathematics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import k5.K5

fun K5.simulateRecursion() {

    noLoop()

    show {
        showCircle(size.width / 2, size.height / 2, 500f)
    }
}

fun DrawScope.showCircle(x: Float, y: Float, d: Float) {
    drawCircle(
        color = Color.White,
        radius = d / 2,
        center = Offset(x, y),
        style = Stroke(5f),
    )
    if (d > 2) {
        val newD = d * 0.5f
        showCircle(x + newD, y, newD)
        showCircle(x - newD, y, newD)
    }
}
