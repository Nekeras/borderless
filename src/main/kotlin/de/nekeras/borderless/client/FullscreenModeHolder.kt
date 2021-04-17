package de.nekeras.borderless.client

import de.nekeras.borderless.client.FullscreenModeHolder.window
import de.nekeras.borderless.client.fullscreen.FullscreenMode
import de.nekeras.borderless.config.Config
import de.nekeras.borderless.config.FocusLossConfig
import de.nekeras.borderless.config.FullscreenModeConfig
import de.nekeras.borderless.logger
import net.minecraft.client.MainWindow
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IWindowEventListener
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.lang.reflect.Field
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
     * Reflection [Field] for the [IWindowEventListener] in the [MainWindow] class.
     */
    private val windowEventListenerField: Field =
        MainWindow::class.java.declaredFields.find { field ->
            field.type == IWindowEventListener::class.java
        }?.let { field ->
            field.isAccessible = true
            field
        } ?: throw IllegalStateException("Could not find WindowEventListener")

    /**
     * Access for the [IWindowEventListener] in the [MainWindow] class that is called on window events.
     */
    private var MainWindow.windowEventListener: IWindowEventListener?
        get() = windowEventListenerField.get(this) as? IWindowEventListener
        set(value) = windowEventListenerField.set(this, value)

    /**
     * The current fullscreen mode of the window.
     */
    private var fullscreenMode: FullscreenMode? by Delegates.observable(null) { _, oldValue, newValue ->
        log.info("Detected fullscreen mode change from $oldValue to $newValue")
        oldValue?.reset(window)
        log.info("Refreshing $fullscreenMode")

        if (window.isFullscreen) {
            newValue?.apply(window)
        } else {
            newValue?.reset(window)
        }
    }

    @JvmStatic
    fun initializeMinecraft() {
        log.info("Replacing default window event listener")

        val oldListener = window.windowEventListener
        window.windowEventListener = SizeChangedWindowEventListener(oldListener) {
            refreshFullscreenModeFromConfig()
        }
    }

    /**
     * Re-applies the current fullscreen mode set in [Config.fullscreenMode] and [Config.focusLoss].
     */
    @JvmStatic
    fun refreshFullscreenModeFromConfig() {
        log.info("Refreshing $fullscreenMode")
        val fullscreenConfig = Config.fullscreenMode.get() ?: FullscreenModeConfig.NATIVE
        val focusLossConfig = Config.focusLoss.get() ?: FocusLossConfig.DO_NOTHING
        fullscreenMode = FullscreenMode.fromConfigEnums(fullscreenConfig, focusLossConfig)
    }
}
