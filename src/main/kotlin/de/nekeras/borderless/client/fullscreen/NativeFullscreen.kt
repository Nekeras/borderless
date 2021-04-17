package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.logger
import net.minecraft.client.MainWindow
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

/**
 * The native Minecraft fullscreen, which will change the monitor's video mode to match the window.
 */
@OnlyIn(Dist.CLIENT)
object NativeFullscreen : FullscreenMode {

    private val log by logger()

    override fun apply(window: MainWindow) {
        log.info("Apply")
    }

    override fun reset(window: MainWindow) {
        log.info("Reset")
    }
}
