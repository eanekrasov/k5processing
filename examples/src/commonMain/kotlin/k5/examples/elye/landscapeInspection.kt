package k5.examples.elye

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import k5.K5
import k5.math.Vector2D
import k5.math.noise2D
import k5.ui.onPointerMove

fun K5.landscapeInspection() {
    val loop = 1000
    val slices = 250
    val mouseInsensitivity = 50
    val mouseVector = Vector2D()
    show(modifier = Modifier.onPointerMove { x, y ->
        mouseVector.x = x
        mouseVector.y = y
    }) {
        for (offset in 0 until slices) {
            for (x in 0 until loop) {
                val noiseInputX = x * 0.002
                val noiseInputY = offset * 0.02
                val valueY = size.height - noise2D(noiseInputX, noiseInputY) * size.height
                val colorRed = (noise2D(noiseInputX, noiseInputY) * 0xFF).toInt()
                val colorGreen = (noise2D(noiseInputX, noiseInputY + 10) * 0xFF).toInt()
                val colorBlue = (noise2D(noiseInputX, noiseInputY + 20) * 0xFF).toInt()

                val pointVector2D = Vector2D(x.toFloat() / loop * size.width, valueY.toFloat())
                drawPoints(
                    listOf(
                        Offset(
                            pointVector2D.x + ((mouseVector.x - size.width / 2) / mouseInsensitivity) * (offset - slices / 2),
                            pointVector2D.y + ((mouseVector.y) / mouseInsensitivity) * (offset - slices / 2)
                        )
                    ),
                    PointMode.Points,
                    Color(colorRed, colorGreen, colorBlue, 0xFF),
                    2f
                )
            }
        }
    }
}
