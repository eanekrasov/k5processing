package k5.examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import k5.K5
import kotlin.random.Random

fun K5.theTenPrint() {

    var x = 0f
    var y = 0f
    val spacing = 40f

    noLoop()

    show {
        while (y <= size.height) {
            if (Random.nextFloat() < 0.5f) {
                drawLine(Color.Cyan, Offset(x, y), Offset(x + spacing, y + spacing))
            } else {
                drawLine(Color.White, Offset(x, y + spacing), Offset(x + spacing, y))
            }

            x += spacing
            if (x > size.width.toInt()) {
                x = 0f
                y += spacing
            }
        }
    }
}
