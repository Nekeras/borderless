package de.nekeras.borderless.fullscreen

import de.nekeras.borderless.extensions.logger
import de.nekeras.borderless.extensions.tryGetMonitor
import net.minecraft.client.MainWindow
import org.lwjgl.glfw.GLFW

/**
 * The native fullscreen mode, but switches to windowed mode on focus loss.
 */
object NativeWindowedFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        log.info("Apply")
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE)
        GLFW.glfwSetWindowFocusCallback(window.handle) { _, focused ->
            if (focused) {
                onFocusGained(window)
            } else {
                onFocusLost(window)
            }
        }
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE)
        GLFW.glfwSetWindowFocusCallback(window.handle, null)
    }

    private fun onFocusGained(window: MainWindow) {
        window.tryGetMonitor(log) { monitor ->
            val videoMode = window.videoMode.orElseGet { monitor.defaultVideoMode }
            GLFW.glfwSetWindowMonitor(
                window.handle,
                monitor.monitorPointer,
                0,
                0,
                videoMode.width,
                videoMode.height,
                videoMode.refreshRate
            )
        }
    }

    private fun onFocusLost(window: MainWindow) {
        GLFW.glfwSetWindowMonitor(
            window.handle,
            0,
            window.windowX,
            window.windowY,
            window.width,
            window.height,
            GLFW.GLFW_DONT_CARE
        )
    }
}
