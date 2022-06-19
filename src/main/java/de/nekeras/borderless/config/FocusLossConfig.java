package de.nekeras.borderless.config;

import de.nekeras.borderless.client.fullscreen.FullscreenDisplayMode;
import net.minecraft.util.OptionEnum;

import javax.annotation.Nonnull;

/**
 * Settings for a focus loss behaviour that is applied on a fullscreen window. These setting will
 * only be applied to a {@link FullscreenDisplayMode#NATIVE native fullscreen window}.
 */
public enum FocusLossConfig implements OptionEnum {

    /**
     * Doesn't do anything when focus on a fullscreen window is lost, the window may be always on
     * top, depending on the operating system.
     */
    DO_NOTHING(0, "Doesn't do anything when focus on a fullscreen window is lost, the window may be "
            + "always on top, depending on the operating system."),

    /**
     * Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the default
     * Minecraft behaviour.
     */
    MINIMIZE(1,
            "Minimizes (iconify) the window when focus on a fullscreen window is lost, this is the "
                    + "default Minecraft behaviour."),

    /**
     * Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window is
     * lost.
     */
    SWITCH_TO_WINDOWED(2,
            "Switches to a windowed mode and leaves the fullscreen when focus on a fullscreen window "
                    + "is lost."),

    ;

    private static final String BASE_KEY = "borderless.config.focus_loss.%s";

    private final int id;
    private final String comment;
    private final String translationKey;

    FocusLossConfig(int id, @Nonnull String comment) {
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
