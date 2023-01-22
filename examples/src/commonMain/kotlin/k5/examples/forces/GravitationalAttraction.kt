@file:Suppress("MemberVisibilityCanBePrivate")

package k5.examples.forces

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5.K5
import k5.math.Vector2D
import k5.math.constrain
import k5.math.divide
import k5.math.k5Random
import k5.math.magSq
import k5.math.multiply
import k5.math.plusAssign
import k5.math.set
import k5.math.setMag
import k5.math.sub
import k5.math.timesAssign
import k5.math.toOffSet
import k5.ui.onPointerMove
import kotlin.math.sqrt
import kotlin.random.Random

data class Moon(val x: Float, val y: Float, val m: Float) {
    val position = Vector2D(x, y)
    val velocity = Vector2D.randomVector().multiply(10f)
    val acceleration = Vector2D(0f, 0f)
    val mass = m
    val r = sqrt(mass) * 2

    fun applyForce(force: Vector2D) {
        acceleration += force.divide(mass)
    }

    fun update() {
        velocity += acceleration
        position += velocity
        acceleration *= 0f
    }

    fun render(drawScope: DrawScope) {
        drawScope.drawCircle(Color.White, r, position.toOffSet())
    }
}

data class Attractor(val x: Float, val y: Float, val m: Float) {
    companion object {
        const val G = 5 // Universal Gravitational Constant
    }

    val position = Vector2D(x, y)
    val mass = m
    val radius = sqrt(mass) * 2

    fun attract(ball: Moon) {
        val attractorPosition = position.copy()
        val force = attractorPosition.sub(ball.position)
        force.setMag(G * (mass * ball.mass) / constrain(force.magSq(), 100f, 1000f))
        ball.applyForce(force)
    }

    fun render(drawScope: DrawScope) {
        drawScope.drawCircle(Color.Magenta, radius, position.toOffSet())
    }
}

fun K5.gravitationalPull() {

    val moons = List(15) {
        Moon(
            k5Random(50, size.width.toInt()),
            k5Random(50, size.height.toInt()),
            Random.nextInt(30, 100).toFloat()
        )
    }

    val attractor = Attractor(size.width / 2, size.height / 2, 600f)

    show(modifier = Modifier.onPointerMove { x, y -> attractor.position.set(Vector2D(x, y)) }) {
        attractor.render(this)
        for (moon in moons) {
            moon.update()
            moon.render(this)
            attractor.attract(moon)
        }
    }
}
