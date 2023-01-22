package k5.math

import androidx.compose.ui.geometry.Offset
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Represents a 2-dimensional vector in plane
 *
 * This class can be used to create a vector for any entity with [x] and [y] as
 * x and y points in canvas plane. The canvas plane follows cartesian coordinate system but with origin at
 * top left corner and positive x-axis along right direction and positive y-axis along downward direction.
 *
 * For ex: Here representation of position vector in canvas plane could be given as -
 * <code>
 *     val position = Vector2D(25f, 25f)
 * </code>
 */
data class Vector2D(var x: Float = 0f, var y: Float = 0f) {

    /**
     * Equals method to check the equality between two vectors
     */
    override fun equals(other: Any?): Boolean {
        return other is Vector2D && other.x == x && other.y == y
    }

    override fun hashCode(): Int {
        return 31 * x.hashCode() + y.hashCode()
    }

    companion object {
        /**
         * Creates a vector from an [angle] and [length]
         *
         * Given any angle in radians and length/magnitude of a vector, it'll create a vector with
         * it's x-component and y-component
         *
         * @param angle Angle in radians
         * @param length length in float
         *
         * @return [Vector2D] vector for given angle and with given magnitude
         */
        fun fromAnAngle(angle: Float, length: Float = 1f): Vector2D {
            return Vector2D(length * cos(angle), length * sin(angle))
        }

        /**
         * Creates a random vector (unit vector)
         * Can be used to create a random unit vector along any direction
         * @return a random unit [Vector2D]
         */
        fun randomVector(): Vector2D {
            return fromAnAngle((Random.nextFloat() * PI * 2).toFloat())
        }
    }
}

/*Below are few simple vector algebra methods*/

fun Vector2D.add(other: Vector2D): Vector2D = apply {
    x += other.x
    y += other.y
}

operator fun Vector2D.plusAssign(other: Vector2D) {
    add(other)
}

/**
 * To add a vector to [this] vector and multiplies the [other] vector with [scalar]
 */
fun Vector2D.addWithScalarMultiply(other: Vector2D, scalar: Float): Vector2D = apply {
    x += other.x * scalar
    y += other.y * scalar
}

/**
 * To subtract a vector from [this] vector
 */
fun Vector2D.sub(other: Vector2D): Vector2D = apply {
    x -= other.x
    y -= other.y
}

operator fun Vector2D.minusAssign(other: Vector2D) {
    sub(other)
}

/**
 * To multiply [this] vector with [x] factor and [y] factor
 */
fun Vector2D.multiply(x: Float, y: Float): Vector2D = apply {
    this.x *= x
    this.y *= y
}

/**
 * To multiply [this] vector with single [factor]
 * See also [scalarMultiply]
 */
fun Vector2D.multiply(factor: Float): Vector2D = multiply(factor, factor)

operator fun Vector2D.timesAssign(factor: Float) {
    multiply(factor)
}

/**
 * To divide [this] vector by [x] and [y] values
 */
fun Vector2D.div(x: Float, y: Float): Vector2D = apply {
    this.x /= x
    this.y /= y
}

/**
 * To divide [this] vector by single [factor]
 */
fun Vector2D.divide(factor: Float): Vector2D = div(factor, factor)

operator fun Vector2D.divAssign(factor: Float) {
    divide(factor)
}

/**
 * Calculates the magnitude (length) of the vector and returns the result asa float
 */
fun Vector2D.mag(): Float = sqrt(magSq())

/**
 * Calculates the squared magnitude of [this] vector and returns the result as a float
 */
fun Vector2D.magSq(): Float = x * x + y * y

/**
 * Calculates the dot product of two vectors
 */
fun Vector2D.dot(other: Vector2D): Float = x * other.x + y * other.y

/**
 * Calculates Euclidean distance between two vectors
 */
fun Vector2D.distance(other: Vector2D): Float = copy().sub(other).mag()

/**
 * Normalizes the vector to length 1 - making unit vector
 *
 * @return [Vector2D]
 */
fun Vector2D.normalize(): Vector2D = apply {
    val len = mag()
    if (len != 0f) scalarMultiply(1 / len)
}

/**
 * Set the magnitude of this vector to the value used for the <b>n</b>
 * parameter.
 *
 * @param n the new length of the vector
 */
fun Vector2D.setMag(n: Float): Vector2D = normalize().multiply(n)

/**
 * Limit the magnitude of this vector to the value used for the <b>max</b>
 * parameter.
 * <code>
 * val v = Vector2D(10, 20);
 * // v has components [10.0, 20.0]
 * v.limit(5);
 * // v's components are set to
 * // [2.2271771, 4.4543543 ]
 * </code>
 */
fun Vector2D.limit(max: Float): Vector2D = apply {
    val magSq = magSq()
    if (magSq > max * max) {
        val norm = sqrt(magSq)
        div(norm, norm).scalarMultiply(max)
    }
}

/**
 * Calculate the angle of rotation for this vector(only 2D vectors).
 */
fun Vector2D.heading(): Float = atan2(y, x).fromRadians()

/**
 * Rotate the vector to a specific angle, magnitude remains the same
 * @param angle - Angle in radians
 */
fun Vector2D.setHeading(angle: Float): Vector2D = apply {
    val mag = mag()
    x = mag * cos(angle)
    y = mag * sin(angle)
}

/**
 * Rotates [this] vector by given [angle]
 */
fun Vector2D.rotate(angle: Float): Vector2D = apply {
    val newHeading = (heading() + angle).toRadians()
    val mag = mag()
    x = cos(newHeading) * mag
    y = sin(newHeading) * mag
}

/**
 * Sets [this] vector as [other] vector
 */
fun Vector2D.set(other: Vector2D): Vector2D = apply {
    x = other.x
    y = other.y
}

/**
 * Returns angle between [this] vector and [other] vector
 *
 * @return Angle in radians
 */
fun Vector2D.angleBetween(other: Vector2D): Float {
    val dotmag = dot(other) / (mag() * other.mag())
    val angle = acos(min(1f, max(-1f, dotmag)).toDouble()).toFloat()
    // angle = angle * Math.signum(c)
    return angle.toRadians()
}

/**
 * Linear interpolate the vector to another vector
 * @param x the x component
 * @param y the y component
 * @param amt the amount of interpolation; some value between 0.0
 *                     (old vector) and 1.0 (new vector). 0.9 is very near
 *                      the new vector. 0.5 is halfway in between.
 */
fun Vector2D.lerp(x: Float, y: Float, amt: Float): Vector2D = apply {
    this.x += (x - this.x) * amt
    this.y += (y - this.y) * amt
}

/**
 * Reflect the incoming vector about a normal to a line in 2D, or about a normal to a plane in 3D
 * This method acts on the vector directly
 * @param surfaceNormal the [Vector2D] to reflect about, will be normalized by this method
 */
fun Vector2D.reflect(surfaceNormal: Vector2D): Vector2D {
    surfaceNormal.normalize()
    return sub(surfaceNormal.scalarMultiply(2 * dot(surfaceNormal)))
}

fun Vector2D.scalarMultiply(scalar: Float): Vector2D = apply {
    x *= scalar
    y *= scalar
}

/**
 * Increments [this] vector by [factor]
 */
operator fun Vector2D.inc(factor: Float): Vector2D = apply {
    x += factor
    y += factor
}

/**
 * Decrements [this] vector by [factor]
 */
operator fun Vector2D.dec(factor: Float): Vector2D = apply {
    x -= factor
    y -= factor
}

/**
 * Converts a [this] [Vector2D] into [Offset] value
 *
 * @return [Offset] with x and y value as x and y component of vector
 */
fun Vector2D.toOffSet(): Offset = Offset(x, y)
