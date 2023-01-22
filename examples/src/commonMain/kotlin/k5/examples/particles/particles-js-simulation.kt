@file:Suppress("MemberVisibilityCanBePrivate")

package k5.examples.particles

import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import k5.K5
import k5.math.Vector2D
import k5.math.distance
import k5.math.divide
import k5.math.multiply
import k5.math.plusAssign
import k5.math.random
import k5.math.toOffSet
import kotlin.math.abs

class Particle(val dimension: Size, val velocityFactor: State<Float>, val distance: State<Float>) {

    val position = Vector2D(
        (0f..dimension.width).random(),
        (0f..dimension.height).random()
    )

    var r = (1f..8f).random()

    var velocity = Vector2D(
        (-2.0f..2.0f).random(),
        (-1.0f..1.5f).random()
    )

    fun DrawScope.createParticle() {
        drawCircle(Color.White, r, position.toOffSet())
    }

    fun moveParticle() {
        velocity.multiply(velocityFactor.value)
        if (position.x < 0f || position.x > dimension.width) {
            velocity.x *= -1
        }
        if (position.y < 0f || position.y > dimension.height) {
            velocity.y *= -1
        }
        position += velocity
        velocity.divide(velocityFactor.value)
    }

    fun DrawScope.joinParticles(particles: List<Particle>) {
        particles.forEach {
            val dist = position.distance(it.position)
            if (dist < distance.value) {
                drawLine(Color.Cyan, position.toOffSet(), it.position.toOffSet(), alpha = 0.5f)
            }
        }
    }
}

fun K5.particleJs() {

    val numberOfParticles = mutableStateOf(50)
    val velocityFactor = mutableStateOf(1f)
    val distance = mutableStateOf(100f)
    val particles = mutableStateListOf<Particle>()
    repeat(numberOfParticles.value) {
        particles.add(Particle(size, velocityFactor, distance))
    }

    showWithControls(controls = {
        Text("Number of particles")
        Slider(
            value = numberOfParticles.value.toFloat(),
            onValueChange = { numberOfParticles.value = it.toInt() },
            valueRange = 50f..200f,
            onValueChangeFinished = {
                if (numberOfParticles.value < particles.size) {
                    repeat(particles.size - numberOfParticles.value) {
                        particles.removeLast()
                    }
                } else {
                    repeat(abs(numberOfParticles.value - particles.size)) {
                        particles.add(Particle(size, velocityFactor, distance))
                    }
                }
            }
        )
        Text("Velocity")
        Slider(
            value = velocityFactor.value,
            onValueChange = { velocityFactor.value = it },
            valueRange = 1f..5f
        )
        Text("Lines")
        Slider(
            value = distance.value,
            onValueChange = { distance.value = it },
            valueRange = 50f..200f
        )
    }) {
        particles.forEachIndexed { index, particle ->
            particle.run {
                createParticle()
                moveParticle()
                joinParticles(particles.slice(0 until index))
            }
        }
    }
}
