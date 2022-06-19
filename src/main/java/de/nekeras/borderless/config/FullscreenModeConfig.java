package de.nekeras.borderless.config;

import net.minecraft.network.chat.Component;
import net.minecraft.util.OptionEnum;

import javax.annotation.Nonnull;

/**
 * An enum storing all supported fullscreen modes that can be configured in the {@link Config}.
 */
public enum FullscreenModeConfig implements OptionEnum {

    /**
     * The best suitable fullscreen mode for the current operating system.
     */
    BEST(0, "The best suitable fullscreen mode for the current operating system."),

    /**
     * A borderless fullscreen which sets the width and height of the window to the monitor's video
     * mode and removing
     * window borders.
     */
    BORDERLESS(1, "A borderless fullscreen which sets the width and height of the window to the "
            + "monitor's video mode and removing window borders."),

    /**
     * A native fullscreen which changes the monitor's window mode in order to apply the fullscreen.
     * Focus loss behaviour can be manually configured using {@link FocusLossConfig}.
     */
    NATIVE(2, "A native fullscreen which changes the monitor's window mode in order to apply the "
            + "fullscreen. Focus loss behaviour can be manually configured using the 'focusLoss' "
            + "option."),

    ;

    private static final String BASE_KEY = "borderless.config.fullscreen_mode.%s";

    private final int id;
    private final String comment;
    private final String translationKey;

    FullscreenModeConfig(int id, @Nonnull String comment) {
        this.id = id;
        this.comment = comment;
        this.translationKey = String.format(BASE_KEY, name().toLowerCase());
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
    public int getId() {
        return id;
    }

    @Nonnull
    @Override
    public String getKey() {
        return translationKey;
    }

    @Override
    public String toString() {
        return translationKey;
    }
}
