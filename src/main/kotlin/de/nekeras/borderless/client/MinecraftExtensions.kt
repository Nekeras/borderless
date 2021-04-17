package de.nekeras.borderless.client

import net.minecraft.client.MainWindow
import net.minecraft.client.Monitor
import org.apache.logging.log4j.Logger
import org.lwjgl.glfw.GLFW

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
 * Retrieves the monitor's name from GLFW.
 */
val Monitor.name: String
    get() = GLFW.glfwGetMonitorName(monitor) ?: "- ERROR -"
