package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.Glfw
import de.nekeras.borderless.Glfw.disableWindowAttribute
import de.nekeras.borderless.Glfw.tryGetMonitor
import de.nekeras.borderless.logger
import net.minecraft.client.MainWindow
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.glfw.GLFW

/**
 * The native fullscreen mode, but switches to windowed mode on focus loss.
 */
@OnlyIn(Dist.CLIENT)
object NativeWindowedFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        super.apply(window)

        window.disableWindowAttribute(Glfw.WindowAttribute.AUTO_ICONIFY)
        GLFW.glfwSetWindowFocusCallback(window.window) { _, focused ->
            if (focused) {
                onFocusGained(window)
            } else {
                onFocusLost(window)
            }
        }
    }

    override fun reset(window: MainWindow) {
        super.reset(window)
        GLFW.glfwSetWindowFocusCallback(window.window, null)
    }

    private fun onFocusGained(window: MainWindow) {
        window.tryGetMonitor(log) { monitor ->
            val videoMode = window.preferredFullscreenVideoMode.orElseGet { monitor.currentMode }
            GLFW.glfwSetWindowMonitor(
                window.window,
                monitor.monitor,
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
            window.window,
            0,
            window.x,
            window.y,
            window.width,
            window.height,
            GLFW.GLFW_DONT_CARE
        )
    }
}
