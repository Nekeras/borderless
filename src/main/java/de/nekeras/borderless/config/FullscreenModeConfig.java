package de.nekeras.borderless.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

    private static final String BASE_KEY = "borderless.config.fullscreen_mode.";
    private static final String KEY_DESCRIPTION_EXTENSION = ".description";

    private final String comment;
    private final String titleKey;
    private final String descriptionKey;

    FullscreenModeConfig(@Nonnull String comment) {
        this(comment, null, null);
    }

    FullscreenModeConfig(@Nonnull String comment, @Nullable String titleKey,
        @Nullable String descriptionKey) {
        this.comment = comment;
        this.titleKey = titleKey == null ? BASE_KEY + name().toLowerCase() : titleKey;
        this.descriptionKey =
            descriptionKey == null ? this.titleKey + KEY_DESCRIPTION_EXTENSION : descriptionKey;
    }

    /**
     * A description comment for this option.
     *
     * @return A comment
     */
    public String getComment() {
        return comment;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    @Override
    public String toString() {
        return titleKey;
    }

}
