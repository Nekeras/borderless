package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.Glfw
import de.nekeras.borderless.Glfw.disableWindowAttribute
import net.minecraft.client.MainWindow
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
@OnlyIn(Dist.CLIENT)
object NativeNonIconifyFullscreen : FullscreenMode {

    override fun apply(window: MainWindow) {
        super.apply(window)

        window.disableWindowAttribute(Glfw.WindowAttribute.AUTO_ICONIFY)
    }
}
