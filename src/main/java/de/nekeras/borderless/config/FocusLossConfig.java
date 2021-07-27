package de.nekeras.borderless.config;

import de.nekeras.borderless.client.fullscreen.FullscreenDisplayMode;
import de.nekeras.borderless.util.Translatable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * Settings for a focus loss behaviour that is applied on a fullscreen window. These setting will
 * only be applied to a {@link FullscreenDisplayMode#NATIVE native fullscreen window}.
 */
public enum FocusLossConfig implements Translatable {

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

    private static final String BASE_KEY = "borderless.config.focus_loss.%s";

    private final String comment;
    private final TranslatableComponent translation;

    FocusLossConfig(String comment) {
        this.comment = comment;
        this.translation = new TranslatableComponent(String.format(BASE_KEY, name().toLowerCase()));
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
    public Component getTranslation() {
        return translation;
    }

    @Override
    public String toString() {
        return translation.getKey();
    }

}
