package k5.examples.simulations

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import k5.K5

data class Rectangles(
    val size: Dp,
    val offsetX: Dp,
    val offsetY: Dp,
    val color: Color
)

fun K5.showRotatingSquares() {

    val rectList = mutableListOf<Rectangles>()
    val n = mutableStateOf(35f)
    val rectSize = mutableStateOf(15.dp)
    var angle = 0f

    val colorList = listOf(
        Color(0xffffeaa7),
        Color(0xfffab1a0),
        Color(0xffa29bfe),
    )

    showWithControls(controls = {
        Text("Number of squares")
        Slider(
            value = n.value,
            valueRange = 10f..40f,
            onValueChange = { n.value = it },
            modifier = Modifier.fillMaxWidth(),
        )
    }) {

        for (i in 0..n.value.toInt()) {
            val s = rectSize.value * i
            rectList.add(Rectangles(s, -s / 2, -s / 2, colorList[i % colorList.size]))
        }

        translate(size.width / 2, size.height / 2) {
            for (i in n.value.toInt() downTo 0) {
                rotate(angle * (n.value.toInt() - i + 1) * 0.07f, Offset(0f, 0f)) {
                    drawRect(
                        brush = SolidColor(rectList[i].color),
                        topLeft = Offset(rectList[i].offsetX.toPx(), rectList[i].offsetY.toPx()),
                        size = Size(rectList[i].size.toPx(), rectList[i].size.toPx()),
                        alpha = 0.7f,
                    )
                }
            }
        }

        angle += 0.5f
    }
}
