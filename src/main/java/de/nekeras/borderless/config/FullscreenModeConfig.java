package de.nekeras.borderless.config;

import javax.annotation.Nonnull;

/**
 * An enum storing all supported fullscreen modes that can be configured in the {@link Config}.
 */
public enum FullscreenModeConfig {

    /**
     * The best suitable fullscreen mode for the current operating system.
     */
    BEST("The best suitable fullscreen mode for the current operating system."),

    /**
     * A borderless fullscreen which sets the width and height of the window to the monitor's video
     * mode and removing
     * window borders.
     */
    BORDERLESS("A borderless fullscreen which sets the width and height of the window to the "
        + "monitor's video mode and removing window borders."),

    /**
     * A native fullscreen which changes the monitor's window mode in order to apply the fullscreen.
     * Focus loss behaviour can be manually configured using {@link FocusLossConfig}.
     */
    NATIVE("A native fullscreen which changes the monitor's window mode in order to apply the "
        + "fullscreen. Focus loss behaviour can be manually configured using the 'focusLoss' "
        + "option."),

    ;

    private static final String BASE_KEY = "borderless.config.fullscreen_mode.%s";

    private final String comment;
    private final String titleKey;

    FullscreenModeConfig(@Nonnull String comment) {
        this.comment = comment;
        this.titleKey = String.format(BASE_KEY, name().toLowerCase());
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
        return titleKey;
    }

}
