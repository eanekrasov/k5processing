@file:Suppress("MemberVisibilityCanBePrivate")

package k5.examples.simulations

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5.K5
import k5.math.Vector2D
import k5.ui.onPointerMove

const val gravity = 5f
const val mass = 2f

// TODO: WIP
class Spring(
    var x: Float,
    var y: Float,
) {

    var vx = 0f // vx velocity on x
    var vy = 0f // vy velocity on y

    val radius = 60f
    val stiffness = 0.2f
    val damping = 0.3f

    fun update(targetX: Float, targetY: Float) {
        val force = (targetX - x) * stiffness
        val ax = force / mass
        vx = damping * (vx + ax)
        x += vx

        var forceY = (targetY - y) * stiffness
        forceY += gravity
        val ay = force / mass
        vy = damping * (vy + ay)
        y += vy
    }

    fun DrawScope.render(nx: Float, ny: Float) {
        drawLine(Color.White, Offset(x, y), Offset(nx, ny))
        drawCircle(Color.White, radius, Offset(x, y), alpha = 0.4f)
    }
}

fun K5.chainLoop() {
    val l1 = Spring(0f, size.width / 2)
    val l2 = Spring(0f, size.width / 2)

    val mouse = Vector2D(0f, 0f)
    show(modifier = Modifier.onPointerMove { x, y ->
        println("$x $y")
        mouse.x = x
        mouse.y = y
    }) {
        l1.run {
            update(mouse.x, mouse.y)
            render(mouse.x, mouse.y)
        }
        // l2.update(l1.x, l1.y)
        // l2.render(l1.x, l1.y, scope)
    }
}
