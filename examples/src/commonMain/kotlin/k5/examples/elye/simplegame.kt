package k5.examples.elye

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import k5.K5
import k5.math.Vector2D
import k5.math.limit
import k5.math.plusAssign
import k5.math.random
import k5.math.set
import k5.math.sub
import k5.math.toOffSet
import k5.ui.onPointerMove

private const val BALL_RADIUS = 30f

fun K5.simpleGame() {
    var velocity = 5f
    val vehicleY = size.height - 250f
    val vehicleStartX = size.width / 2
    val vehiclePosition = Vector2D(vehicleStartX, vehicleY)
    val mouseVector = Vector2D(vehicleStartX, vehicleY)
    fun resetStarX() = (40 until size.width.toInt() - 40).random().toFloat()
    val starPosition = Vector2D(resetStarX(), 0f)
    fun resetStar() {
        starPosition.set(Vector2D(resetStarX(), 0f))
    }

    fun hitEndPoint() = starPosition.y >= size.height
    fun moveStar() {
        starPosition += Vector2D(0f, velocity)
    }

    fun collide() =
        (kotlin.math.abs(starPosition.x - vehiclePosition.x) < BALL_RADIUS * 2) &&
            (kotlin.math.abs(starPosition.y - vehiclePosition.y) < BALL_RADIUS * 2)
    show(modifier = Modifier.onPointerMove { x, _ ->
        mouseVector.x = x
        mouseVector.y = vehicleY
    }) {
        vehiclePosition += mouseVector.copy().sub(vehiclePosition).limit(5f)

        when {
            hitEndPoint() -> noLoop()
            collide() -> {
                resetStar(); velocity++
            }

            else -> moveStar()
        }
        drawCircle(color = Color.Red, radius = BALL_RADIUS, center = starPosition.toOffSet())
        drawCircle(color = Color.White, radius = BALL_RADIUS, center = vehiclePosition.toOffSet())
    }
}
