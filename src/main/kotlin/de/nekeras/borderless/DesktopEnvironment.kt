package de.nekeras.borderless

import de.nekeras.borderless.fullscreen.BorderlessFullscreen
import de.nekeras.borderless.fullscreen.FullscreenMode
import de.nekeras.borderless.fullscreen.NativeFullscreen
import de.nekeras.borderless.fullscreen.NativeNonIconifyFullscreen
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.lwjgl.system.Platform

/**
 * Possible desktop environments Minecraft can be run on. Each environment may have a [bestFullscreenMode]
 * fullscreen mode that should be used when executed in such an environment.
 */
enum class DesktopEnvironment(val bestFullscreenMode: FullscreenMode) {

    /**
     * The Windows operating system desktop environment.
     */
    WINDOWS(BorderlessFullscreen),

    /**
     * X11 window system used by Linux operating systems.
     */
    X11(NativeNonIconifyFullscreen),

    /**
     * Wayland display server used by Linux operating system.
     */
    WAYLAND(NativeFullscreen),

    /**
     * A generic desktop environment that does not fit in any other category.
     */
    GENERIC(NativeFullscreen);

    companion object {

        private const val LINUX_WINDOW_SYSTEM_VARIABLE = "XDG_SESSION_TYPE";
        private const val X11_NAME = "x11"
        private const val WAYLAND_NAME = "wayland"

        private val log: Logger = LogManager.getLogger(DesktopEnvironment::class.java)

        @JvmStatic
        val current: DesktopEnvironment =
            when (Platform.get()) {
                Platform.WINDOWS -> WINDOWS
                Platform.LINUX -> when (System.getenv(LINUX_WINDOW_SYSTEM_VARIABLE)) {
                    X11_NAME -> X11
                    WAYLAND_NAME -> WAYLAND
                    else -> GENERIC
                }
                else -> GENERIC // Treat macOS as Generic
            }.also {
                log.info("Found desktop environment $it")
            }

    }

}
