package k5.examples.simulations

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import k5.K5
import k5.math.map
import k5.ui.onPointerMove

fun K5.showWaveMaker() {

    var angle = 0f
    var pointer = 0f
    val colorsList = listOf(Color.White, Color.Gray, Color.Cyan, Color.Blue, Color.Green, Color.Yellow, Color.Red)

    show(modifier = Modifier.onPointerMove { x, _ -> pointer = map(x, 0f, size.width, 1f, 30f) }) {
        val n = 25
        val circleRadius = (size.width / n + n / 3)
        val spacing = circleRadius + n / 3
        val dotRadius = circleRadius / 7
        val diff = spacing - circleRadius
        var offsetX = 0f
        var offsetY = 0f
        for (row in 0..n) {
            for (col in 0..n) {
                // it.drawCircle(
                //     SolidColor(Color.White),
                //     radius = circleRadius,
                //     center = Offset(offsetX, offsetY),
                //     style = Stroke(width = 3f),
                //     alpha = 0.3f
                // )
                withTransform({
                    rotate((angle + (row * col) + (col + row) * pointer) % 360, Offset(offsetX, offsetY - diff - circleRadius))
                }) {
                    drawCircle(
                        brush = Brush.linearGradient(colorsList),
                        radius = dotRadius,
                        center = Offset(offsetX - diff, offsetY - diff)
                    )
                }
                offsetX += spacing
            }
            offsetX = 0f
            offsetY += spacing
        }
        angle += 0.01f * 360f
    }
}
