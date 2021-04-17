package de.nekeras.borderless.client.fullscreen

import de.nekeras.borderless.client.DesktopEnvironment
import de.nekeras.borderless.config.FocusLossConfig
import de.nekeras.borderless.config.FullscreenModeConfig
import net.minecraft.client.MainWindow
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

/**
 * A fullscreen mode that can be applied for the Minecraft [MainWindow]. The [apply] method
 * will be apply this fullscreen mode on the supplied window instance, the [reset] method may
 * revert all changes that were made.
 */
@OnlyIn(Dist.CLIENT)
interface FullscreenMode {

    /**
     * Applies this fullscreen mode on the supplied [window].
     */
    fun apply(window: MainWindow)

    /**
     * Resets this fullscreen mod on the supplied [window], reverting any changes that were made
     * in [apply].
     */
    fun reset(window: MainWindow)

    companion object {

        /**
         * The best available fullscreen mode for the current [DesktopEnvironment]. The same as
         * calling [DesktopEnvironment.bestFullscreenMode] on [DesktopEnvironment.current].
         */
        @JvmStatic
        val best = DesktopEnvironment.current.bestFullscreenMode

        /**
         * Converts config enum values to an actual implementation of a [FullscreenMode].
         */
        @JvmStatic
        fun fromConfigEnums(fullscreenMode: FullscreenModeConfig, focusLoss: FocusLossConfig) =
            when (fullscreenMode) {
                FullscreenModeConfig.BEST -> best
                FullscreenModeConfig.BORDERLESS -> BorderlessFullscreen
                FullscreenModeConfig.NATIVE -> when (focusLoss) {
                    FocusLossConfig.DO_NOTHING -> NativeFullscreen
                    FocusLossConfig.MINIMIZE -> NativeNonIconifyFullscreen
                    FocusLossConfig.SWITCH_TO_WINDOWED -> NativeWindowedFullscreen
                }
            }
    }
}
