package de.nekeras.borderless.client.fullscreen

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

/**
 * The native Minecraft fullscreen, which will change the monitor's video mode to match the window.
 */
@OnlyIn(Dist.CLIENT)
object NativeFullscreen : FullscreenMode
