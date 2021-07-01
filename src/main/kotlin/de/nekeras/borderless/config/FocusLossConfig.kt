package de.nekeras.borderless.config

import net.minecraft.util.text.TranslationTextComponent

/**
 * Settings for a focus loss behaviour that is applied on a fullscreen window. These setting will
 * only be applied when using [FullscreenModeConfig.NATIVE].
 */
enum class FocusLossConfig(override val comment: String) : ConfigEnum {

    /**
     * Doesn't do anything when focus on a fullscreen window is lost, the window may be always on
     * top, depending on the operating system.
     */
    DO_NOTHING(
        "Doesn't do anything when focus on a fullscreen window is lost, the window may be " +
            "always on top, depending on the operating system."
    ),

    /**
     * Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the default
     * Minecraft behaviour.
     */
    MINIMIZE(
        "Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the " +
            "default Minecraft behaviour."
    ),

    /**
     * Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window is
     * lost.
     */
    SWITCH_TO_WINDOWED(
        "Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window " +
            "is lost."
    )

    ;

    override val translation = TranslationTextComponent("borderless.config.focus_loss.${name.lowercase()}")
}
