package k5.examples.angularmotion

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotateRad
import k5.K5
import k5.math.Vector2D
import k5.math.constrain
import k5.math.map
import k5.math.toOffSet
import k5.ui.onPointerMove

fun K5.rotateRectangle() {

    var angle = 0f
    var angularVelocity = 0f
    var angularAcc: Float

    val position = Vector2D(size.width / 2, size.height / 2).toOffSet()

    var mouseX = 0f
    show(modifier = Modifier.onPointerMove { x, _ -> mouseX = x }) {

        // Map the angular acceleration between -0.01f to 0.01f.
        // Map function basically re-maps a number from one range to another.
        angularAcc = map(mouseX, 0f, size.width, -0.01f, 0.01f)

        // add constrain on the angular velocity value between given low and high
        angularVelocity = constrain(angularVelocity, -0.2f, 0.2f)

        rotateRad(angle, pivot = size.center) {
            drawRect(
                color = Color.Cyan,
                topLeft = Offset(position.x - 30, position.y - 90),
                size = Size(60f, 180f),
            )
        }

        // THe concept of angular velocity and angular acceleration works the same way!
        // add angular velocity to angular acceleration
        angularVelocity += angularAcc
        // Add angular velocity to angle
        angle += angularVelocity
    }
}
