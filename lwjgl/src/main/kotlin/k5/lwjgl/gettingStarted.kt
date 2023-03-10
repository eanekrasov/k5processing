package k5.lwjgl

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL

internal fun gettingStarted() {
    val window = init()
    loop(window)
    terminate(window)
}

// region init

private fun init(): Long {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!GLFW.glfwInit()) throw IllegalStateException("Unable to initialize GLFW")

    // Configure GLFW
    GLFW.glfwDefaultWindowHints() // optional, the current window hints are already the default
    GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE) // the window will stay hidden after creation
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE) // the window will be resizable

    // Create the window
    val window = GLFW.glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
    if (window == NULL) throw RuntimeException("Failed to create the GLFW window")

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    GLFW.glfwSetKeyCallback(window) { hWnd, key, _, action, _ ->
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(hWnd, true) // We will detect this in the rendering loop
    }
    MemoryStack.stackPush().use { stack ->
        val pWidth = stack.mallocInt(1) // int*
        val pHeight = stack.mallocInt(1) // int*

        // Get the window size passed to glfwCreateWindow
        GLFW.glfwGetWindowSize(window, pWidth, pHeight)

        // Get the resolution of the primary monitor
        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!

        // Center the window
        GLFW.glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2)
    }

    // Make the OpenGL context current
    GLFW.glfwMakeContextCurrent(window)
    // Enable v-sync
    GLFW.glfwSwapInterval(1)

    // Make the window visible
    GLFW.glfwShowWindow(window)

    return window
}

// endregion init

// region loop

private fun loop(window: Long) {
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities()

    // Set the clear color
    GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while (!GLFW.glfwWindowShouldClose(window)) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT) // clear the framebuffer
        GLFW.glfwSwapBuffers(window) // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        GLFW.glfwPollEvents()
    }
}

// endregion loop

// region terminate

private fun terminate(window: Long) {
    // Free the window callbacks and destroy the window
    Callbacks.glfwFreeCallbacks(window)
    GLFW.glfwDestroyWindow(window)

    // Terminate GLFW and free the error callback
    GLFW.glfwTerminate()
    GLFW.glfwSetErrorCallback(null)?.free()
}

// endregion terminate
