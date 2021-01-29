package de.nekeras.borderless.config

import net.minecraftforge.common.ForgeConfigSpec

object Config {

    private const val FULLSCREEN_MODE_PATH = "fullscreenMode"
    private const val FOCUS_LOSS_PATH = "focusLoss"

    private val builder = ForgeConfigSpec.Builder()

    /**
     * The fullscreen mode to use, for more information see [FullscreenModeConfig].
     *
     * @see FullscreenModeConfig
     */
    val fullscreenMode: ForgeConfigSpec.EnumValue<FullscreenModeConfig> = builder
        .comment(*FullscreenModeConfig.values().toComment())
        .defineEnum(FULLSCREEN_MODE_PATH, FullscreenModeConfig.BEST)

    /**
     * The focus loss behaviour to use, for more information see [FocusLossConfig].
     *
     * @see FocusLossConfig
     */
    val focusLoss: ForgeConfigSpec.EnumValue<FocusLossConfig> = builder
        .comment(*FocusLossConfig.values().toComment())
        .defineEnum(FOCUS_LOSS_PATH, FocusLossConfig.MINIMIZE)

    /**
     * The final configuration specification.
     */
    val configSpec: ForgeConfigSpec = builder.build()

    /**
     * Converts an array of [ConfigEnum] values to a comment array describing each value.
     */
    private fun <T : ConfigEnum> Array<T>.toComment() =
        map { mode -> "${mode.name} - ${mode.comment}" }.toTypedArray()

}
