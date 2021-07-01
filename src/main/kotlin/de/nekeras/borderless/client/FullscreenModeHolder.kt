package de.nekeras.borderless.client

import de.nekeras.borderless.client.FullscreenModeHolder.window
import de.nekeras.borderless.client.fullscreen.FullscreenMode
import de.nekeras.borderless.client.listener.SizeChangedWindowEventListener
import de.nekeras.borderless.config.Config
import de.nekeras.borderless.logger
import de.nekeras.borderless.makeFieldAccessible
import net.minecraft.client.MainWindow
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IWindowEventListener
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import kotlin.properties.Delegates

/**
 * Creates a new fullscreen mode holder for the supplied [window]. This will also automatically
 * replace the original's [IWindowEventListener] of the window with a new instance of
 * [SizeChangedWindowEventListener].
 */
@OnlyIn(Dist.CLIENT)
object FullscreenModeHolder {

    private val log by logger()
    private val window: MainWindow = Minecraft.getInstance().window

    /**
     * Access for the [IWindowEventListener] in the [MainWindow] class that is called on window events.
     */
    private var MainWindow.windowEventListener by makeFieldAccessible<MainWindow, IWindowEventListener>()

    /**
     * The current fullscreen mode of the window.
     */
    private var fullscreenMode: FullscreenMode? by Delegates.observable(null) { _, oldValue, newValue ->
        log.info("Detected fullscreen mode change from $oldValue to $newValue")
        oldValue?.reset(window)
        log.info("Refreshing $newValue")

        newValue?.let {
            if (window.isFullscreen) {
                it.apply(window)
            } else {
                it.reset(window)
            }
        }
    }

    /**
     * Initializes the Minecraft instance to work with the custom [SizeChangedWindowEventListener].
     */
    @JvmStatic
    fun initializeMinecraft() {
        log.info("Replacing default window event listener")

        val oldListener = window.windowEventListener
        window.windowEventListener = SizeChangedWindowEventListener(oldListener) {
            refreshFullscreenModeFromConfig()
        }

        refreshFullscreenModeFromConfig()
    }

    /**
     * Re-applies the current fullscreen mode set in the [Config].
     */
    @JvmStatic
    fun refreshFullscreenModeFromConfig() {
        log.info("Refreshing fullscreen mode from config to ${Config.fullscreenMode}")
        fullscreenMode = Config.fullscreenMode
    }
}
