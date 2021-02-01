package de.nekeras.borderless

import de.nekeras.borderless.extensions.logger
import de.nekeras.borderless.fullscreen.FullscreenMode
import de.nekeras.borderless.fullscreen.NativeFullscreen
import net.minecraft.client.MainWindow
import net.minecraft.client.renderer.IWindowEventListener
import java.lang.reflect.Field
import kotlin.properties.Delegates

/**
 * Creates a new fullscreen mode holder for the supplied [window]. This will also automatically
 * replace the original's [IWindowEventListener] of the window with a new instance of
 * [SizeChangedWindowEventListener].
 */
class FullscreenModeHolder(private val window: MainWindow) {

    init {
        log.info("Replacing default window event listener")

        val oldListener = window.windowEventListener
        window.windowEventListener = SizeChangedWindowEventListener(oldListener) {
            refreshFullscreenMode(fullscreenMode ?: NativeFullscreen)
        }
    }

    /**
     * The current fullscreen mode of the window.
     */
    var fullscreenMode: FullscreenMode? by Delegates.observable(null) { _, oldValue, newValue ->
        log.info("Detected fullscreen mode change from $oldValue to $newValue")
        oldValue?.reset(window)
        refreshFullscreenMode(newValue ?: NativeFullscreen)
    }

    /**
     * Re-applies the supplied fullscreen mode [fullscreenMode].
     */
    private fun refreshFullscreenMode(fullscreenMode: FullscreenMode) {
        log.info("Refreshing $fullscreenMode")

        if (window.isFullscreen) {
            fullscreenMode.apply(window)
        } else {
            fullscreenMode.reset(window)
        }
    }

    companion object {

        private val log by logger()

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
    }
}
