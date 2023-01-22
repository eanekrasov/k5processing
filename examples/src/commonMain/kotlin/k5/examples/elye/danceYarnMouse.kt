package k5.examples.elye

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import k5.K5
import k5.math.Vector2D
import k5.math.noise3D
import k5.ui.onPointerMove

fun K5.danceYarnMouse() {
    val mouseVector = Vector2D()
    show(modifier = Modifier.onPointerMove { x, y ->
        mouseVector.x = x
        mouseVector.y = y
    }) {
        var offset = 0.0
        val m2d = mouseVector.x * 0.002
        val m3d = mouseVector.y * 0.002
        for (i in 1000 until 1200) {
            fun noiseX(variant: Double) = 2 * size.width * noise3D(variant, m2d, m3d)
            fun noiseY(variant: Double) = 2 * size.height * noise3D(offset + variant, m2d, m3d)
            fun color(variant: Double) = 0xFF - (0xFF * noise3D(variant, m2d, m3d)).toInt()
            val x = FloatArray(3) { index -> noiseX(offset + 5 + index * 10).toFloat() }
            val y = FloatArray(3) { index -> noiseY(offset + 5 + index * 10).toFloat() }
            val path = Path().apply {
                moveTo(mouseVector.x, mouseVector.y)
                cubicTo(x[0], y[0], x[1], y[1], x[2], y[2])
            }
            val color = Color(
                red = color(offset + 35),
                green = color(offset + 25),
                blue = color(offset + 15),
            )
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 0.3f),
            )
            offset += 0.002
        }
    }
}
