package de.nekeras.borderless.fullscreen

import de.nekeras.borderless.extensions.logger
import de.nekeras.borderless.extensions.name
import de.nekeras.borderless.extensions.tryGetMonitor
import net.minecraft.client.MainWindow
import org.lwjgl.glfw.GLFW

/**
 * The window decoration will be removed and the window will be maximized to simulate the fullscreen
 * for a window.
 */
object BorderlessFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        window.tryGetMonitor(log) { monitor ->
            val videoMode = monitor.defaultVideoMode
            val x = monitor.virtualPosX
            val y = monitor.virtualPosY
            val width = videoMode.width
            val height = videoMode.height

            log.info("Apply on monitor ${monitor.name} at ($x | $y) size ($width x $height)")
            GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE)
            GLFW.glfwSetWindowMonitor(window.handle, 0, x, y, width, height, GLFW.GLFW_DONT_CARE)
        }
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE)
    }
}
