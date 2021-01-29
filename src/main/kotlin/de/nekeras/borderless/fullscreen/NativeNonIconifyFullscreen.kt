package de.nekeras.borderless.fullscreen

import net.minecraft.client.MainWindow
import org.lwjgl.glfw.GLFW

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
object NativeNonIconifyFullscreen : FullscreenMode {

    override fun apply(window: MainWindow) {
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE)
    }

    override fun reset(window: MainWindow) {
        GLFW.glfwSetWindowAttrib(window.handle, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE)
    }

}
