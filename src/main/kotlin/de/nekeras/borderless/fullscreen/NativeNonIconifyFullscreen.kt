package de.nekeras.borderless.fullscreen

import de.nekeras.borderless.extensions.logger
import net.minecraft.client.MainWindow
import org.lwjgl.glfw.GLFW

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
object NativeNonIconifyFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        log.info("Apply")
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE)
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE)
    }
}
