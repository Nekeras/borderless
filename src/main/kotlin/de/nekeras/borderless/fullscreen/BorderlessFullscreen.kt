package de.nekeras.borderless.fullscreen

import de.nekeras.borderless.extensions.logger
import de.nekeras.borderless.extensions.tryGetMonitor
import net.minecraft.client.MainWindow
import org.lwjgl.glfw.GLFW

/**
 * The window decoration will be removed and the window will be maximized to simulate the fullscreen
 * for a window.
 */
object BorderlessFullscreen : FullscreenMode {

    private val log = logger<BorderlessFullscreen>()

    override fun apply(window: MainWindow) {
        window.tryGetMonitor(log) { monitor ->
            val videoMode = monitor.defaultVideoMode
            GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE)
            GLFW.glfwSetWindowMonitor(
                    window.handle,
                    0,
                    monitor.virtualPosX,
                    monitor.virtualPosY,
                    videoMode.width,
                    videoMode.height,
                    GLFW.GLFW_DONT_CARE
            )
        }
    }

    override fun reset(window: MainWindow) {
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE)
    }

}
