package de.nekeras.borderless

import net.minecraft.client.MainWindow
import net.minecraft.client.Monitor
import org.apache.logging.log4j.Logger
import org.lwjgl.glfw.GLFW

/**
 * Helper class for access for various GLFW functions with logging support.
 */
object Glfw {

    private val log by logger()

    /**
     * Supported window attributes that are used by the mod.
     */
    enum class WindowAttribute(val bit: Int, val enabledByDefault: Boolean) {

        DECORATED(GLFW.GLFW_DECORATED, true),
        AUTO_ICONIFY(GLFW.GLFW_AUTO_ICONIFY, true)
    }

    /**
     * Retrieves the monitor's name from GLFW.
     */
    val Monitor.name: String
        get() = GLFW.glfwGetMonitorName(monitor) ?: "- ERROR -"

    /**
     * Tries to get the monitor for the window. If it could be found and is non-null, the [action]
     * callback will be invoked. If the monitor is null, an error message will be printed to the
     * supplied [logger] and the function returns without calling [action].
     */
    inline fun MainWindow.tryGetMonitor(logger: Logger, action: (Monitor) -> Unit) {
        findBestMonitor()?.let { action(it) }
            ?: logger.error("Window's current monitor could not be retrieved")
    }

    /**
     * Enables a window [attribute].
     */
    fun MainWindow.enableWindowAttribute(attribute: WindowAttribute) {
        log.info("Enable window attribute $attribute")

        GLFW.glfwSetWindowAttrib(window, attribute.bit, GLFW.GLFW_TRUE)
    }

    /**
     * Disables a window [attribute].
     */
    fun MainWindow.disableWindowAttribute(attribute: WindowAttribute) {
        log.info("Disable window attribute $attribute")

        GLFW.glfwSetWindowAttrib(window, attribute.bit, GLFW.GLFW_FALSE)
    }

    /**
     * Restores all default window attributes that are supported by the [WindowAttribute] enum.
     */
    fun MainWindow.applyDefaultWindowAttributes() {
        log.info("Resetting window attributes")

        WindowAttribute.values().forEach {
            if (it.enabledByDefault) {
                enableWindowAttribute(it)
            } else {
                disableWindowAttribute(it)
            }
        }

        log.info("Done resetting window attributes")
    }
}
