package k5.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 * Set global angle mode DEGREES/RADIANS [AngleMode]
 */
var angleMode = AngleMode.RADIANS

fun Float.tan(): Float = tan(toRadians())

fun Float.sin(): Float = sin(toRadians())

fun Float.cos(): Float = cos(toRadians())

fun atan2(y: Float, x: Float): Float {
    return kotlin.math.atan2(y.toDouble(), x.toDouble()).toFloat().toRadians()
}

fun Float.atan(): Float = kotlin.math.atan(this).toRadians()

fun Float.asin(): Float = kotlin.math.asin(this).toRadians()

fun Float.acos(): Float = kotlin.math.acos(this).toRadians()

/**
 * Converts float to radians
 */
fun Float.toRadians(): Float {
    return if (angleMode == AngleMode.DEGREES) this * DEG_TO_RAD.toFloat() else this
}

/**
 * Converts float to degrees
 */
fun Float.toDegrees(): Float {
    if (angleMode == AngleMode.RADIANS) {
        return this * RAD_TO_DEG.toFloat()
    }
    return this
}

/**
 * Converts [this] degrees to radians
 */
fun Float.fromRadians(): Float {
    if (angleMode == AngleMode.RADIANS) {
        return this * RAD_TO_DEG.toFloat()
    }
    return this
}
