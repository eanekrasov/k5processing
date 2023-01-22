package k5.examples.particles

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import k5.K5
import k5.math.map
import k5.math.random

data class Star(val dimens: Size) {
    val width = dimens.width
    val height = dimens.height
    var x = (-width..width).random()
    var y = (-height..height).random()
    var z = (0f..width).random()
    var pz = z

    fun update() {
        z -= 20
        if (z < 1) {
            z = dimens.width
            x = (-width..width).random()
            y = (-height..height).random()
            pz = z
        }
    }

    fun DrawScope.show() {
        val sx = map(x / z, -1f, 1f, -dimens.width, dimens.width)
        val sy = map(y / z, -1f, 1f, -dimens.height, dimens.height)
        val r = map(z, 0f, dimens.width, 16f, 0f)
        drawCircle(Color.White, r, Offset(sx, sy))

        val px = map(x / pz, -1f, 1f, -dimens.width, dimens.width)
        val py = map(y / pz, -1f, 1f, -dimens.height, dimens.height)
        val stroke = map(pz, 0f, dimens.width, 16f, 1f)
        pz = z
        drawLine(Color.White, Offset(px, py), Offset(sx, sy), strokeWidth = stroke)
    }
}

fun K5.showStarField() {
    val stars = List(600) { Star(size) }

    show {
        translate(size.width / 2, size.height / 2) {
            for (star in stars) star.run {
                update()
                show()
            }
        }
    }
}
