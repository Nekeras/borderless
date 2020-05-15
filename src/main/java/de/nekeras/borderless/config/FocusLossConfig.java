package de.nekeras.borderless.config;

import de.nekeras.borderless.fullscreen.FullscreenMode;

/**
 * Settings for a focus loss behaviour that is applied on a fullscreen window. These setting will
 * only be applied to a {@link FullscreenMode#NATIVE native fullscreen window}.
 */
public enum FocusLossConfig {

    /**
     * Doesn't do anything when focus on a fullscreen window is lost, the window may be always on
     * top, depending on the operating system.
     */
    DO_NOTHING("Doesn't do anything when focus on a fullscreen window is lost, the window may be "
        + "always on top, depending on the operating system."),

    /**
     * Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the default
     * Minecraft behaviour.
     */
    MINIMIZE(
        "Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the "
            + "default Minecraft behaviour."),

    /**
     * Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window is
     * lost.
     */
    SWITCH_TO_WINDOWED(
        "Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window "
            + "is lost."),

    ;

    private static final String BASE_TRANSLATION_KEY = "borderless.config.focus_loss.%s";

    private final String comment;
    private final String translationKey;

    FocusLossConfig(String comment) {
        this.comment = comment;
        this.translationKey = String.format(BASE_TRANSLATION_KEY, name().toLowerCase());
    }

    /**
     * A description comment for this option.
     *
     * @return A comment
     */
    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return translationKey;
    }

}
