@file:Suppress("MemberVisibilityCanBePrivate", "UNUSED_VARIABLE")

package k5.examples.noise

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize
import k5.K5
import k5.math.TWO_PI
import k5.math.Vector2D
import k5.math.k5Random
import k5.math.limit
import k5.math.noise2D
import k5.math.plusAssign
import k5.math.setMag
import k5.math.timesAssign
import k5.math.toOffSet

var inc = 0.1f
var scl = 10

data class Particle(val dimens: IntSize) {

    val position = Vector2D(k5Random(max = dimens.width), k5Random(max = dimens.height))
    val velocity = Vector2D(0f, 0f)
    val acceleration = Vector2D(0f, 0f)
    val maxSpeed = 4f
    var skipPoint = false
    val points = mutableListOf(position.toOffSet())

    fun update() {
        velocity += acceleration
        velocity.limit(maxSpeed)
        position += velocity
        acceleration *= 0f
    }

    fun follow(force: Vector2D) {
        val
            x = position.x / scl
        val y = position.y / scl
        applyForce(force)
    }

    private fun applyForce(force: Vector2D) {
        acceleration += force
    }

    fun DrawScope.render() {
        points.add(position.toOffSet())
        drawPoints(points, pointMode = PointMode.Polygon, color = Color.White)
    }

    fun edges() {
        skipPoint = false
        if (position.x > dimens.width) {
            position.x = 0f
            skipPoint = true
        }
        if (position.x < 0) {
            position.x = dimens.width.toFloat()
            skipPoint = true
        }
        if (position.y > dimens.height) {
            position.y = 0f
            skipPoint = true
        }
        if (position.y < 0) {
            position.y = dimens.height.toFloat()
            skipPoint = true
        }
    }
}

fun K5.simplexNoise() {

    val particle = Particle(IntSize(size.width.toInt(), size.height.toInt()))
    var yoff = 0f
    var xoff = 0f
    show {
        val angle = noise2D(x = xoff.toDouble(), y = yoff.toDouble()) * TWO_PI + 4
        val v = Vector2D.fromAnAngle(angle.toFloat())
        v.setMag(1f)
        xoff += inc
        yoff += inc

        particle.apply {
            follow(v)
            update()
            edges()
            render()
        }
    }
}
