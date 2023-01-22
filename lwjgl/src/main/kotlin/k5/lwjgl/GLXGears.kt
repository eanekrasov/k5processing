@file:Suppress("UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName", "PrivatePropertyName", "MemberVisibilityCanBePrivate")

package k5.lwjgl

import k5.lwjgl.GLFWUtil.glfwInvoke
import org.joml.*
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import java.io.IOException
import java.nio.*

internal class GLXGears {
    private val gear1: Gear
    private val gear2: Gear
    private val gear3: Gear
    private var program = 0
    private val positions: Int
    private val normals: Int
    private val u_NORMAL: Int
    private val u_MVP: Int
    private val u_LIGHT: Int
    private val u_COLOR: Int

    // ---------------------
    private val P = Matrix4d()
    private val MVP = Matrix4d()
    private val V = Matrix4x3d()
    private val M = Matrix4x3d()
    private val MV = Matrix4x3d()
    private val normal = Matrix3d()
    private val light = Vector3d()
    private val vec3f = BufferUtils.createFloatBuffer(3)
    private val mat3f = BufferUtils.createFloatBuffer(3 * 3)
    private val mat4f = BufferUtils.createFloatBuffer(4 * 4)

    // ---------------------
    private var count = 0L
    private var startTime: Double
    private val distance = 40.0
    private var angle = 0.0

    init {
        System.err.println("GL_VENDOR: ${GL11C.glGetString(GL11C.GL_VENDOR)}")
        System.err.println("GL_RENDERER: ${GL11C.glGetString(GL11C.GL_RENDERER)}")
        System.err.println("GL_VERSION: ${GL11C.glGetString(GL11C.GL_VERSION)}")
        val caps = GL.getCapabilities()
        check(caps.OpenGL20) { "This demo requires OpenGL 2.0 or higher." }
        GL11C.glEnable(GL11C.GL_CULL_FACE)
        GL11C.glEnable(GL11C.GL_DEPTH_TEST)
        P.setFrustum(-1.0, 1.0, -1.0, 1.0, 5.0, 100.0)
        try {
            program = compileShaders(
                version = when {
                    caps.OpenGL33 -> 330
                    caps.OpenGL21 -> 120
                    else -> 110
                },
                vertexShader = IOUtil.ioResourceToByteBuffer("gears/gears.vert", 4096),
                fragmentShader = IOUtil.ioResourceToByteBuffer("gears/gears.frag", 4096)
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        u_MVP = GL20C.glGetUniformLocation(program, "u_MVP")
        u_NORMAL = GL20C.glGetUniformLocation(program, "u_NORMAL")
        u_LIGHT = GL20C.glGetUniformLocation(program, "u_LIGHT")
        u_COLOR = GL20C.glGetUniformLocation(program, "u_COLOR")
        positions = GL20C.glGetAttribLocation(program, "in_Position")
        normals = GL20C.glGetAttribLocation(program, "in_Normal")
        if (caps.OpenGL30) GL30C.glBindVertexArray(GL30C.glGenVertexArrays()) // bind and forget
        GL20C.glEnableVertexAttribArray(positions)
        GL20C.glEnableVertexAttribArray(normals)
        gear1 = Gear(1.0, 4.0, 1.0, 20, 0.7, floatArrayOf(0.8f, 0.1f, 0.0f, 1.0f))
        gear2 = Gear(0.5, 2.0, 2.0, 10, 0.7, floatArrayOf(0.0f, 0.8f, 0.2f, 1.0f))
        gear3 = Gear(1.3, 2.0, 0.5, 10, 0.7, floatArrayOf(0.2f, 0.2f, 1.0f, 1.0f))
        startTime = System.currentTimeMillis() / 1000.0
    }

    private fun animate() {
        angle += 2.0
    }

    private fun setSize(width: Int, height: Int) {
        val h = height / width.toFloat()
        GL11C.glViewport(0, 0, width, height)
        if (h < 1.0f) {
            P.setFrustum(-1.0 / h, 1.0 / h, -1.0, 1.0, 5.0, 100.0)
        } else {
            P.setFrustum(-1.0, 1.0, -h.toDouble(), h.toDouble(), 5.0, 100.0)
        }
    }

    private fun render() {
        GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT or GL11C.GL_DEPTH_BUFFER_BIT)
        // VIEW
        V.translation(0.0, 0.0, -distance).rotateX(20.0f * Math.PI / 180).rotateY(30.0f * Math.PI / 180)
        //V.rotateZ(0.0f * PI / 180);
        // LIGHT
        GL20C.glUniform3fv(u_LIGHT, V.transformDirection(light.set(5.0, 5.0, 10.0)).normalize()[vec3f])
        // GEAR 1
        M.translation(-3.0, -2.0, 0.0).rotateZ(angle * Math.PI / 180)
        drawGear(gear1)
        // GEAR 2
        M.translation(3.1, -2.0, 0.0).rotateZ((-2.0 * angle - 9.0) * Math.PI / 180)
        drawGear(gear2)
        // GEAR 3
        M.translation(-3.1, 4.2, 0.0).rotateZ((-2.0 * angle - 25.0) * Math.PI / 180)
        drawGear(gear3)
        count++
        val theTime = System.currentTimeMillis() / 1000.0
        if (theTime >= startTime + 1.0) {
            System.out.format("%d fps\n", count)
            startTime = theTime
            count = 0
        }
    }

    private fun drawGear(gear: Gear) {
        GL20C.glUniformMatrix3fv(u_NORMAL, false, V.mul(M, MV).normal(normal)[mat3f])
        GL20C.glUniformMatrix4fv(u_MVP, false, P.mul(MV, MVP)[mat4f])
        GL20C.glUniform4fv(u_COLOR, gear.color)
        gear.bind(positions, normals)
        GL11C.glDrawArrays(GL11C.GL_TRIANGLES, 0, gear.vertexCount)
    }

    private class Gear(inner_radius: Double, outer_radius: Double, width: Double, teeth: Int, tooth_depth: Double, color: FloatArray) {
        val color: FloatBuffer = BufferUtils.createFloatBuffer(4).apply {
            put(color).flip()
        }
        val positionVBO = GL15C.glGenBuffers()
        val normalVBO = GL15C.glGenBuffers()
        val vertexCount = Builder()
            .build(inner_radius, outer_radius, width, teeth, tooth_depth)
            .updateVBO(positionVBO, normalVBO)

        private class Builder {
            private var vertexCount = 0
            private var positions: FloatBuffer? = null
            private var normals: FloatBuffer? = null
            private var normalX = 0.0
            private var normalY = 0.0
            private var normalZ = 0.0
            private val quads = DoubleArray(4 * 3)
            private var quadCount = 0
            fun build(inner_radius: Double, outer_radius: Double, width: Double, teeth: Int, tooth_depth: Double): Builder {
                positions = MemoryUtil.memAllocFloat(2000 * 3)
                normals = MemoryUtil.memAllocFloat(2000 * 3)
                val r1 = outer_radius - tooth_depth / 2.0
                val r2 = outer_radius + tooth_depth / 2.0
                var angle: Double
                var da = 2.0 * Math.PI / teeth / 4.0
                var u: Double
                var v: Double
                var len: Double
                normal3f(0.0, 0.0, 1.0)
                /* draw front face */
                quadCount = 0
                for (i in 0..teeth) {
                    angle = i * 2.0 * Math.PI / teeth
                    vertex3f(inner_radius * Math.cos(angle), inner_radius * Math.sin(angle), width * 0.5)
                    vertex3f(r1 * Math.cos(angle), r1 * Math.sin(angle), width * 0.5)
                    if (i < teeth) {
                        vertex3f(inner_radius * Math.cos(angle), inner_radius * Math.sin(angle), width * 0.5)
                        vertex3f(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), width * 0.5)
                    }
                }

                /* draw front sides of teeth */
                da = 2.0 * Math.PI / teeth / 4.0
                for (i in 0 until teeth) {
                    angle = i * 2.0 * Math.PI / teeth
                    quadCount = 0
                    vertex3f(r1 * Math.cos(angle), r1 * Math.sin(angle), width * 0.5)
                    vertex3f(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), width * 0.5)
                    vertex3f(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), width * 0.5)
                    vertex3f(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), width * 0.5)
                }
                normal3f(0.0, 0.0, -1.0)
                /* draw back face */
                quadCount = 0
                for (i in 0..teeth) {
                    angle = i * 2.0 * Math.PI / teeth
                    vertex3f(r1 * Math.cos(angle), r1 * Math.sin(angle), -width * 0.5)
                    vertex3f(inner_radius * Math.cos(angle), inner_radius * Math.sin(angle), -width * 0.5)
                    if (i < teeth) {
                        vertex3f(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), -width * 0.5)
                        vertex3f(inner_radius * Math.cos(angle), inner_radius * Math.sin(angle), -width * 0.5)
                    }
                }
                /* draw back sides of teeth */
                da = 2.0 * Math.PI / teeth / 4.0
                for (i in 0 until teeth) {
                    angle = i * 2.0 * Math.PI / teeth
                    quadCount = 0
                    vertex3f(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), -width * 0.5)
                    vertex3f(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), -width * 0.5)
                    vertex3f(r1 * Math.cos(angle), r1 * Math.sin(angle), -width * 0.5)
                    vertex3f(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), -width * 0.5)
                }
                /* draw outward faces of teeth */
                quadCount = 0
                for (i in 0 until teeth) {
                    angle = i * 2.0 * Math.PI / teeth
                    vertex3f(r1 * Math.cos(angle), r1 * Math.sin(angle), width * 0.5)
                    vertex3f(r1 * Math.cos(angle), r1 * Math.sin(angle), -width * 0.5)
                    u = r2 * Math.cos(angle + da) - r1 * Math.cos(angle)
                    v = r2 * Math.sin(angle + da) - r1 * Math.sin(angle)
                    len = Math.sqrt(u * u + v * v)
                    u /= len
                    v /= len
                    normal3f(v, -u, 0.0)
                    vertex3f(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), width * 0.5)
                    vertex3f(r2 * Math.cos(angle + da), r2 * Math.sin(angle + da), -width * 0.5)
                    normal3f(Math.cos(angle), Math.sin(angle), 0.0)
                    vertex3f(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), width * 0.5)
                    vertex3f(r2 * Math.cos(angle + 2 * da), r2 * Math.sin(angle + 2 * da), -width * 0.5)
                    u = r1 * Math.cos(angle + 3 * da) - r2 * Math.cos(angle + 2 * da)
                    v = r1 * Math.sin(angle + 3 * da) - r2 * Math.sin(angle + 2 * da)
                    normal3f(v, -u, 0.0)
                    vertex3f(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), width * 0.5)
                    vertex3f(r1 * Math.cos(angle + 3 * da), r1 * Math.sin(angle + 3 * da), -width * 0.5)
                    normal3f(Math.cos(angle), Math.sin(angle), 0.0)
                }
                vertex3f(r1 * Math.cos(0f), r1 * Math.sin(0f), width * 0.5)
                vertex3f(r1 * Math.cos(0f), r1 * Math.sin(0f), -width * 0.5)
                /* draw inside radius cylinder */quadCount = 0
                for (i in 0..teeth) {
                    angle = (if (i == teeth) 0 else i) * 2.0 * Math.PI / teeth // Map 2*PI to 0 to get an exact hash below
                    normal3f(-Math.cos(angle), -Math.sin(angle), 0.0)
                    vertex3f(inner_radius * Math.cos(angle), inner_radius * Math.sin(angle), -width * 0.5)
                    vertex3f(inner_radius * Math.cos(angle), inner_radius * Math.sin(angle), width * 0.5)
                }
                /* Emulate glShadeModel(GL_SMOOTH) for inside radius cylinder */
                val smoothMap: MutableMap<Vector3f, Vector3f> = HashMap(teeth * 2)
                // Sum normals around same position
                for (i in vertexCount - teeth * 6 until vertexCount) {
                    val x = normals!!.get(i * 3)
                    val y = normals!!.get(i * 3 + 1)
                    val z = normals!!.get(i * 3 + 2)
                    smoothMap.compute(Vector3f(positions!!.get(i * 3), positions!!.get(i * 3 + 1), positions!!.get(i * 3 + 2))) { _, normal ->
                        if (normal == null) Vector3f(x, y, z) else normal.add(x, y, z)
                    }
                }
                // Normalize
                smoothMap.values.forEach { it.normalize() }
                // Apply smooth normals
                for (i in vertexCount - teeth * 6 until vertexCount) {
                    val normal = smoothMap[Vector3f(positions!!.get(i * 3), positions!!.get(i * 3 + 1), positions!!.get(i * 3 + 2))]
                    normals!!.put(i * 3, normal!!.x)
                    normals!!.put(i * 3 + 1, normal.y)
                    normals!!.put(i * 3 + 2, normal.z)
                }
                return this
            }

            private fun normal3f(x: Double, y: Double, z: Double) {
                normalX = x
                normalY = y
                normalZ = z
            }

            private fun vertex3f(x: Double, y: Double, z: Double) {
                quads[quadCount * 3] = x
                quads[quadCount * 3 + 1] = y
                quads[quadCount * 3 + 2] = z
                if (++quadCount == 4) {
                    addVertex(quads[0], quads[1], quads[2])
                    addVertex(quads[3], quads[4], quads[5])
                    addVertex(quads[6], quads[7], quads[8])
                    addVertex(quads[6], quads[7], quads[8])
                    addVertex(quads[3], quads[4], quads[5])
                    addVertex(quads[9], quads[10], quads[11])
                    System.arraycopy(quads, 2 * 3, quads, 0, 2 * 3)
                    quadCount = 2
                }
            }

            private fun addVertex(x: Double, y: Double, z: Double) {
                positions!!.put(vertexCount * 3, x.toFloat())
                positions!!.put(vertexCount * 3 + 1, y.toFloat())
                positions!!.put(vertexCount * 3 + 2, z.toFloat())
                normals!!.put(vertexCount * 3, normalX.toFloat())
                normals!!.put(vertexCount * 3 + 1, normalY.toFloat())
                normals!!.put(vertexCount * 3 + 2, normalZ.toFloat())
                vertexCount++
            }

            fun updateVBO(positionVBO: Int, normalVBO: Int): Int {
                // VBO for vertex data
                positions!!.limit(vertexCount * 3)
                GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, positionVBO)
                GL15C.glBufferData(GL15C.GL_ARRAY_BUFFER, positions!!, GL15C.GL_STATIC_DRAW)
                // VBO for normals data
                normals!!.limit(vertexCount * 3)
                GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, normalVBO)
                GL15C.glBufferData(GL15C.GL_ARRAY_BUFFER, normals!!, GL15C.GL_STATIC_DRAW)
                MemoryUtil.memFree(positions!!)
                MemoryUtil.memFree(normals!!)
                positions = null
                normals = null
                return vertexCount
            }
        }

        fun bind(positions: Int, normals: Int) {
            GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, positionVBO)
            GL20C.glVertexAttribPointer(positions, 3, GL11C.GL_FLOAT, false, 0, 0)
            GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, normalVBO)
            GL20C.glVertexAttribPointer(normals, 3, GL11C.GL_FLOAT, false, 0, 0)
        }
    }

    companion object {
        private fun printShaderInfoLog(obj: Int) {
            if (GL20C.glGetShaderi(obj, GL20C.GL_INFO_LOG_LENGTH) > 0) {
                GL20C.glGetShaderInfoLog(obj)
                System.out.format("%s\n", GL20C.glGetShaderInfoLog(obj))
            }
        }

        private fun printProgramInfoLog(obj: Int) {
            if (GL20C.glGetProgrami(obj, GL20C.GL_INFO_LOG_LENGTH) > 0) {
                GL20C.glGetProgramInfoLog(obj)
                System.out.format("%s\n", GL20C.glGetProgramInfoLog(obj))
            }
        }

        private fun compileShader(version: Int, shader: Int, code: ByteBuffer) {
            MemoryStack.stackPush().use { stack ->
                val header = stack.ASCII("#version $version\n#line 0\n", false)
                GL20C.glShaderSource(shader, stack.pointers(header, code), stack.ints(header.remaining(), code.remaining()))
                GL20C.glCompileShader(shader)
                printShaderInfoLog(shader)
                check(GL20C.glGetShaderi(shader, GL20C.GL_COMPILE_STATUS) == GL11C.GL_TRUE) { "Failed to compile shader." }
            }
        }

        private fun compileShaders(version: Int, vertexShader: ByteBuffer, fragmentShader: ByteBuffer): Int {
            val v = GL20C.glCreateShader(GL20C.GL_VERTEX_SHADER).also { compileShader(version, it, vertexShader) }
            val f = GL20C.glCreateShader(GL20C.GL_FRAGMENT_SHADER).also { compileShader(version, it, fragmentShader) }
            return GL20C.glCreateProgram().also {
                GL20C.glAttachShader(it, v)
                GL20C.glAttachShader(it, f)
                GL20C.glLinkProgram(it)
                printProgramInfoLog(it)
                check(GL20C.glGetProgrami(it, GL20C.GL_LINK_STATUS) == GL11C.GL_TRUE) { "Failed to link program." }
                GL20C.glUseProgram(it)
            }
        }

        fun run() {
            val allocator = GLFWAllocator.calloc()
                .allocate { size, user -> MemoryUtil.nmemAllocChecked(size) }
                .reallocate { block, size, user -> MemoryUtil.nmemReallocChecked(block, size) }
                .deallocate { block, user -> MemoryUtil.nmemFree(block) }
            var debugProc: Callback? = null
            var window = MemoryUtil.NULL

            val WIDTH = 300
            val HEIGHT = 300

            try {
                var xpos = 0
                var ypos = 0
                var width = 0
                var height = 0

                // region init

                GLFW.glfwInitAllocator(allocator)
                GLFWErrorCallback.createPrint().set()
                check(GLFW.glfwInit()) { "Unable to initialize glfw" }
                GLFW.glfwDefaultWindowHints()
                GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
                GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
                GLFW.glfwWindowHint(GLFW.GLFW_SCALE_TO_MONITOR, GLFW.GLFW_TRUE)
                GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE)
                if (Platform.get() === Platform.MACOSX) GLFW.glfwWindowHint(GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW.GLFW_FALSE)
                window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "GLFW Gears Demo", MemoryUtil.NULL, MemoryUtil.NULL)
                if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")
                GLFW.glfwSetWindowSizeLimits(window, WIDTH, HEIGHT, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE)
                //glfwSetWindowAspectRatio(window, 1, 1);
                val monitor = GLFW.glfwGetPrimaryMonitor()
                val videoMode = requireNotNull(GLFW.glfwGetVideoMode(monitor))
                // Center window
                GLFW.glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2)
                GLFW.glfwSetKeyCallback(window) { windowHnd, key, scancode, action, mods ->
                    if (action == GLFW.GLFW_RELEASE) when (key) {
                        GLFW.GLFW_KEY_ESCAPE -> GLFW.glfwSetWindowShouldClose(windowHnd, true)
                        GLFW.GLFW_KEY_A -> GLFW.glfwRequestWindowAttention(windowHnd)
                        GLFW.GLFW_KEY_F -> if (GLFW.glfwGetWindowMonitor(windowHnd) == MemoryUtil.NULL) {
                            MemoryStack.stackPush().use { s ->
                                val a = s.ints(0)
                                val b = s.ints(0)
                                GLFW.glfwGetWindowPos(windowHnd, a, b)
                                xpos = a[0]
                                ypos = b[0]
                                GLFW.glfwGetWindowSize(windowHnd, a, b)
                                width = a[0]
                                height = b[0]
                            }
                            GLFW.glfwSetWindowMonitor(windowHnd, monitor, 0, 0, videoMode.width(), videoMode.height(), videoMode.refreshRate())
                            GLFW.glfwSwapInterval(1)
                        }
                        GLFW.GLFW_KEY_G -> GLFW.glfwSetInputMode(windowHnd, GLFW.GLFW_CURSOR, if (GLFW.glfwGetInputMode(windowHnd, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_NORMAL) GLFW.GLFW_CURSOR_DISABLED else GLFW.GLFW_CURSOR_NORMAL)
                        GLFW.GLFW_KEY_O -> GLFW.glfwSetWindowOpacity(window, if (GLFW.glfwGetWindowOpacity(window) == 1.0f) 0.5f else 1.0f)
                        GLFW.GLFW_KEY_R -> GLFW.glfwSetWindowAttrib(windowHnd, GLFW.GLFW_RESIZABLE, 1 - GLFW.glfwGetWindowAttrib(windowHnd, GLFW.GLFW_RESIZABLE))
                        GLFW.GLFW_KEY_U -> GLFW.glfwSetWindowAttrib(windowHnd, GLFW.GLFW_DECORATED, 1 - GLFW.glfwGetWindowAttrib(windowHnd, GLFW.GLFW_DECORATED))
                        GLFW.GLFW_KEY_W -> if (GLFW.glfwGetWindowMonitor(windowHnd) != MemoryUtil.NULL) { GLFW.glfwSetWindowMonitor(windowHnd, MemoryUtil.NULL, xpos, ypos, width, height, 0) }
                    }
                }
                GLFW.glfwMakeContextCurrent(window)
                GL.createCapabilities { MemoryUtil.memCallocPointer(it) }
                debugProc = GLUtil.setupDebugMessageCallback()
                val gears = GLXGears()
                GLFW.glfwSetFramebufferSizeCallback(window) { _, w, h -> if (w != 0 && h != 0) gears.setSize(w, h) }
                GLFW.glfwSetWindowRefreshCallback(window) { windowHnd ->
                    gears.render()
                    gears.animate()
                    GLFW.glfwSwapBuffers(windowHnd)
                }
                GLFW.glfwSwapInterval(1)
                GLFW.glfwShowWindow(window)
                glfwInvoke(window) { _, w, h -> if (w != 0 && h != 0) gears.setSize(w, h) }

                // endregion init

                // region loop

                var lastUpdate = System.currentTimeMillis()
                var frames = 0
                while (!GLFW.glfwWindowShouldClose(window)) {
                    GLFW.glfwPollEvents()
                    gears.render()
                    gears.animate()
                    GLFW.glfwSwapBuffers(window)
                    frames++
                    val time = System.currentTimeMillis()
                    val UPDATE_EVERY = 5 // seconds
                    if (UPDATE_EVERY * 1000L <= time - lastUpdate) {
                        lastUpdate = time
                        System.out.printf("%d frames in %d seconds = %.2f fps\n", frames, UPDATE_EVERY, frames / UPDATE_EVERY.toFloat())
                        frames = 0
                    }
                }

                // endregion loop

            } finally {
                try {
                    MemoryUtil.memFree(GL.getCapabilities().addressBuffer)
                    GL.setCapabilities(null)
                    debugProc?.free()
                    if (window != MemoryUtil.NULL) {
                        Callbacks.glfwFreeCallbacks(window)
                        GLFW.glfwDestroyWindow(window)
                    }
                    GLFW.glfwTerminate()
                    GLFW.glfwSetErrorCallback(null)?.free()
                    allocator.deallocate().free()
                    allocator.reallocate().free()
                    allocator.allocate().free()
                    allocator.free()
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }
}
