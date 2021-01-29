package de.nekeras.borderless.extensions

import net.minecraft.client.MainWindow
import net.minecraft.client.Monitor
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Gets a logger retrieved by [LogManager]. This function uses explicit classes to speed up the
 * logger creation, such that the calling class does not have to be retrieved by LogManager.
 */
inline fun <reified T> logger(): Logger =
    LogManager.getLogger(T::class.java)

/**
 * Tries to get the monitor for the window. If it could be found and is non-null, the [action]
 * callback will be invoked. If the monitor is null, an error message will be printed to the
 * supplied [logger] and the function returns without calling [action].
 */
inline fun MainWindow.tryGetMonitor(logger: Logger, action: (Monitor) -> Unit) =
    monitor?.let { action(it) }
        ?: logger.error("Window's current monitor could not be retrieved")
