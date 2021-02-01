package de.nekeras.borderless.config

/**
 * All supported fullscreen modes that may be selected in the config file.
 */
enum class FullscreenModeConfig(override val comment: String) : ConfigEnum {

    /**
     * The best suitable fullscreen mode for the current operating system.
     */
    BEST("The best suitable fullscreen mode for the current operating system."),

    /**
     * A borderless fullscreen which sets the width and height of the window to the monitor's video
     * mode and removing
     * window borders.
     */
    BORDERLESS(
        "A borderless fullscreen which sets the width and height of the window to the " +
            "monitor's video mode and removing window borders."
    ),

    /**
     * A native fullscreen which changes the monitor's window mode in order to apply the fullscreen.
     * Focus loss behaviour can be manually configured using [FocusLossConfig].
     */
    NATIVE(
        "A native fullscreen which changes the monitor's window mode in order to apply the " +
            "fullscreen. Focus loss behaviour can be manually configured using the 'focusLoss' " +
            "option."
    )

    ;

    override val translationKey = "borderless.config.fullscreen_mode.${name.toLowerCase()}"
}
