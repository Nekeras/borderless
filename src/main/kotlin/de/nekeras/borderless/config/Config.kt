package de.nekeras.borderless.config

import de.nekeras.borderless.client.fullscreen.BorderlessFullscreen
import de.nekeras.borderless.client.fullscreen.FullscreenMode
import de.nekeras.borderless.client.fullscreen.NativeFullscreen
import de.nekeras.borderless.client.fullscreen.NativeNonIconifyFullscreen
import de.nekeras.borderless.client.fullscreen.NativeWindowedFullscreen
import net.minecraftforge.common.ForgeConfigSpec

object Config {

    private const val ENABLED_PATH = "enabled"
    private const val FULLSCREEN_MODE_PATH = "fullscreenMode"
    private const val FOCUS_LOSS_PATH = "focusLoss"

    private val builder = ForgeConfigSpec.Builder()

    /**
     * Whether the mod should be enabled.
     */
    val enabledConfig: ForgeConfigSpec.BooleanValue = builder.define(ENABLED_PATH, true)

    /**
     * The fullscreen mode to use, for more information see [FullscreenModeConfig].
     *
     * @see FullscreenModeConfig
     */
    val fullscreenModeConfig: ForgeConfigSpec.EnumValue<FullscreenModeConfig> = builder
        .comment(*FullscreenModeConfig.values().toComment())
        .defineEnum(FULLSCREEN_MODE_PATH, FullscreenModeConfig.BEST)

    /**
     * The focus loss behaviour to use, for more information see [FocusLossConfig].
     *
     * @see FocusLossConfig
     */
    val focusLossConfig: ForgeConfigSpec.EnumValue<FocusLossConfig> = builder
        .comment(*FocusLossConfig.values().toComment())
        .defineEnum(FOCUS_LOSS_PATH, FocusLossConfig.MINIMIZE)

    /**
     * The final configuration specification.
     */
    val configSpec: ForgeConfigSpec = builder.build()

    /**
     * The currently configured [FullscreenMode] to use.
     */
    val fullscreenMode: FullscreenMode
        get() =
            if (enabledConfig.get()) {
                when (fullscreenModeConfig.get() ?: FullscreenModeConfig.BEST) {
                    FullscreenModeConfig.BEST -> FullscreenMode.best
                    FullscreenModeConfig.BORDERLESS -> BorderlessFullscreen
                    FullscreenModeConfig.NATIVE -> when (focusLossConfig.get() ?: FocusLossConfig.DO_NOTHING) {
                        FocusLossConfig.DO_NOTHING -> NativeFullscreen
                        FocusLossConfig.MINIMIZE -> NativeNonIconifyFullscreen
                        FocusLossConfig.SWITCH_TO_WINDOWED -> NativeWindowedFullscreen
                    }
                }
            } else {
                NativeFullscreen
            }

    /**
     * Converts an array of [ConfigEnum] values to a comment array describing each value.
     */
    private fun <T : ConfigEnum> Array<T>.toComment() =
        map { mode -> "${mode.name} - ${mode.comment}" }.toTypedArray()
}
