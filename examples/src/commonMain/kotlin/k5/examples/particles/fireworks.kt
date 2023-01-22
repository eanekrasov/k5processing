@file:Suppress("MemberVisibilityCanBePrivate")

package k5.examples.particles

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5.K5
import k5.math.Vector2D
import k5.math.map
import k5.math.multiply
import k5.math.plusAssign
import k5.math.random
import k5.math.toOffSet
import kotlin.random.Random

val gravity = Vector2D(0f, 0.2f)

data class FireWorkParticle(
    val x: Float,
    val y: Float,
    val hu: Float,
    val isFireWork: Boolean
) {
    val position = Vector2D(x, y)
    var lifespan = 255f
    val acceleration = Vector2D(0f, 0f)
    val velocity: Vector2D

    init {
        if (isFireWork) {
            velocity = Vector2D(0f, (-15f..-18f).random())
        } else {
            velocity = Vector2D.randomVector()
            velocity.multiply((2f..10f).random())
        }
    }

    fun applyForece(force: Vector2D) {
        acceleration += force
    }

    fun update() {
        if (!isFireWork) {
            velocity.multiply(0.9f)
            lifespan -= 4
        }
        velocity += acceleration
        position += velocity
        acceleration.multiply(0f)
    }

    fun isDone() = lifespan < 0

    @ExperimentalGraphicsApi
    fun DrawScope.show() {
        if (!isFireWork) {
            drawCircle(
                color = Color.hsv(hu, 1f, 1f),
                radius = 5f,
                center = position.toOffSet(),
                alpha = map(lifespan, 255f, 1f, 1f, 0f),
            )
        } else {
            drawRect(
                color = Color.hsv(hu, 1f, 1f),
                topLeft = position.toOffSet(),
                size = Size(5f, 10f),
            )
            drawCircle(
                color = Color.hsv(hu, 1f, 1f),
                radius = 5f,
                center = Offset(position.x + 2f, position.y),
            )
        }
    }
}

data class FireWork(val dimens: Size) {
    val hue = (0f..360f).random()
    val firework = FireWorkParticle((1f..dimens.width).random(), dimens.height, hue, true)
    var isExploded = false
    val particles = mutableListOf<FireWorkParticle>()

    fun isDone() = isExploded && particles.isEmpty()

    fun update() {
        if (!isExploded) {
            firework.applyForece(gravity)
            firework.update()
            if (firework.velocity.y >= 0f) {
                isExploded = true
                explode()
            }
        }

        if (particles.isNotEmpty()) {
            for (i in particles.size - 1 downTo 0) {
                particles[i].applyForece(gravity)
                particles[i].update()
                particles.removeAll { it.isDone() }
            }
        }
    }

    fun explode() {
        repeat(100) {
            particles.add(FireWorkParticle(firework.position.x, firework.position.y, hue, false))
        }
    }

    @ExperimentalGraphicsApi
    fun DrawScope.show() {
        if (!isExploded) firework.run { show() }
        particles.forEach { it.run { show() } }
    }
}

@ExperimentalGraphicsApi
fun K5.showFireWorks() {

    val fireworks = MutableList(10) { FireWork(size) }

    show {

        if (Random.nextFloat() < 0.04f) {
            fireworks.add(FireWork(size))
        }

        for (i in (fireworks.size - 1) downTo 0) {
            fireworks[i].run {
                update()
                show()
            }

            fireworks.removeAll { it.isDone() }
        }
    }
}
