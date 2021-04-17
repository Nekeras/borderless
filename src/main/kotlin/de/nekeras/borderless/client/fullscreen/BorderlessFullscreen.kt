package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.client.name
import de.nekeras.borderless.client.tryGetMonitor
import de.nekeras.borderless.logger
import net.minecraft.client.MainWindow
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.glfw.GLFW

/**
 * The window decoration will be removed and the window will be maximized to simulate the fullscreen
 * for a window.
 */
@OnlyIn(Dist.CLIENT)
object BorderlessFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        window.tryGetMonitor(log) { monitor ->
            val videoMode = monitor.currentMode
            val x = monitor.x
            val y = monitor.y
            val width = videoMode.width
            val height = videoMode.height

            log.info("Apply on monitor ${monitor.name} at ($x | $y) size ($width x $height)")
            GLFW.glfwSetWindowAttrib(window.window, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE)
            GLFW.glfwSetWindowMonitor(window.window, 0, x, y, width, height, GLFW.GLFW_DONT_CARE)
        }
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
        GLFW.glfwSetWindowAttrib(window.window, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE)
    }
}
