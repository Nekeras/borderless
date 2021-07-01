package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.Glfw
import de.nekeras.borderless.Glfw.disableWindowAttribute
import de.nekeras.borderless.Glfw.name
import de.nekeras.borderless.Glfw.tryGetMonitor
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
        super.apply(window)
        
        window.tryGetMonitor(log) { monitor ->
            val videoMode = monitor.currentMode
            val x = monitor.x
            val y = monitor.y
            val width = videoMode.width
            val height = videoMode.height

            log.info("Apply on monitor ${monitor.name} at ($x | $y) size ($width x $height)")
            window.disableWindowAttribute(Glfw.WindowAttribute.DECORATED)
            GLFW.glfwSetWindowMonitor(window.window, 0, x, y, width, height, GLFW.GLFW_DONT_CARE)
        }
    }
}
