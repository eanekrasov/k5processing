@file:Suppress("unused")

package k5.examples.elye

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import k5.K5
import k5.math.noise2D

fun K5.perlinNoise2d() {
    val loop = 3000
    noLoop()
    show {
        for (offset in 0 until loop) {
            val colorRed = (0..255).random()
            val colorGreen = (0..255).random()
            val colorBlue = (0..255).random()
            for (x in 0 until loop) {
                val valueY = noise2D(x * 0.002, offset * 0.002) * size.height * 2
                drawPoints(
                    listOf(Offset(x.toFloat() / loop * size.width, valueY.toFloat())),
                    PointMode.Points,
                    Color(colorRed, colorGreen, colorBlue, 0x88),
                    2f,
                )
            }
        }
    }
}
