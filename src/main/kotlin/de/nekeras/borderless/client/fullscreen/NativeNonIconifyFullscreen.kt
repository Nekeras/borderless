package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.logger
import net.minecraft.client.MainWindow
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.glfw.GLFW

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
@OnlyIn(Dist.CLIENT)
object NativeNonIconifyFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        log.info("Apply")
        GLFW.glfwSetWindowAttrib(window.window, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE)
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
        GLFW.glfwSetWindowAttrib(window.window, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE)
    }
}
