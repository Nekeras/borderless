package de.nekeras.borderless.fullscreen

import net.minecraft.client.MainWindow

/**
 * The native Minecraft fullscreen, which will change the monitor's video mode to match the window.
 */
object NativeFullscreen : FullscreenMode {
    
    override fun apply(window: MainWindow) {}

    override fun reset(window: MainWindow) {}

}
