package k5.lwjgl

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI
import org.lwjgl.glfw.GLFWWindowSizeCallbackI
import org.lwjgl.system.MemoryStack

object GLFWUtil {
    /**
     * Invokes the specified callbacks using the current window and framebuffer sizes of the specified GLFW window.
     *
     * @param window            the GLFW window
     * @param windowSizeCallback      the window size callback, may be null
     * @param framebufferSizeCallback the framebuffer size callback, may be null
     */
    fun glfwInvoke(
        window: Long,
        windowSizeCallback: GLFWWindowSizeCallbackI? = null,
        framebufferSizeCallback: GLFWFramebufferSizeCallbackI?
    ) {
        MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            if (windowSizeCallback != null) {
                GLFW.glfwGetWindowSize(window, w, h)
                windowSizeCallback.invoke(window, w[0], h[0])
            }
            if (framebufferSizeCallback != null) {
                GLFW.glfwGetFramebufferSize(window, w, h)
                framebufferSizeCallback.invoke(window, w[0], h[0])
            }
        }
    }
}