package de.nekeras.borderless.fullscreen

import de.nekeras.borderless.extensions.logger
import net.minecraft.client.MainWindow

/**
 * The native Minecraft fullscreen, which will change the monitor's video mode to match the window.
 */
object NativeFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        log.info("Apply")
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
    }
}
