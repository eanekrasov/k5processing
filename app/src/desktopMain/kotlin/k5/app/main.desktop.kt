@file:JvmName("desktop")

package k5.app

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import k5.app.ui.*
import k5.examples.*
import k5.examples.angularmotion.*
import k5.examples.art.*
import k5.examples.elye.*
import k5.examples.forces.*
import k5.examples.kinematics.*
import k5.examples.mathematics.*
import k5.examples.noise.*
import k5.examples.particles.*
import k5.examples.shm.*
import k5.examples.simulations.*
import k5.examples.vectors.*
import k5.scripting.evalWithLog

@ExperimentalGraphicsApi
@ExperimentalComposeUiApi
fun main(vararg args: String) {
    val icon = BitmapPainter(useResource("ic_launcher.png", ::loadImageBitmap))
    k5.k5(
        size = Size(1000f, 1000f),
        title = "K5 Compose Playground",
        icon = icon,
        undecorated = false,
        resizable = true,
        enabled = true,
        focusable = true,
        alwaysOnTop = false,
        onPreviewKeyEvent = { false },
        onKeyEvent = { false },
    ) {
//         simpleRandomWalk()
//         levyFlightWalker()
//         simpleMover()
//         bouncingBall()
//         gravitationalPull()
//         rotateRectangle()
//         gameOfLife()
//         chainLoop()
//         particleJs()
//         parametricEquation()
//         simplexNoise()
//         simulateSineWave()
//         simulateRecursion()
//         showStarField()
//         showPhyllotaxis()
//         theTenPrint()
//         simplexNoise()
//         showYarns()
//         showBlackHole()
//         showFireWorks()
//         showCircleLoop()
//         showRain()
//         showSkyline()
//         showRotatingSquares()
//         showMatrixGreenRain()
//         showRecursiveTree()
//         showWaveMaker()
//         showCircleGrid()
//         showHearts()
//         simpleGame()
//         mandelbrot()
//         perlinnoise1d()
//         perlinnoise2d()
//         classicyarnanimated()
//         danceYarnMouse()
//         danceYarnAuto()
//         landscapeInspection()
//         showPietMondrian()
//         showCirclePacking()
//         showTriangularMesh()

//         preserveDrawBufferTest()
//         showAbstractDots()
//         showVoid()
//         hypnoticSquares()

        showWithControls(controls = {
            Button({ evalWithLog(particlesJs) }) { Text("Eval") }
        }) {
            drawCircle(Color.Red, 10f, center)
        }
    }
}

val particlesJs = """
    
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

    
    val numberOfParticles = mutableStateOf(50)
    val velocityFactor = mutableStateOf(1f)
    val distance = mutableStateOf(100f)
    val particles = mutableStateListOf<Particle>()
    repeat(numberOfParticles.value) {
        particles.add(Particle(size, velocityFactor, distance))
    }

    sketch {
        particles.forEachIndexed { index, particle ->
            particle.run {
                createParticle()
                moveParticle()
                joinParticles(particles.slice(0 until index))
            }
        }
    }

""".trimIndent()