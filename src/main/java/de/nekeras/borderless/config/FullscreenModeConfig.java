package de.nekeras.borderless.config;

import de.nekeras.borderless.DesktopEnvironment;
import de.nekeras.borderless.fullscreen.*;

import javax.annotation.Nonnull;

/**
 * An enum storing all supported fullscreen modes that can be configured in the {@link Config}.
 */
public enum FullscreenModeConfig {

    /**
     * The best suitable fullscreen mode for the current operating system.
     */
    DEFAULT("The best suitable fullscreen mode for the current operating system."),

    /**
     * A borderless fullscreen which sets the width and height of the window to the monitor's video mode and removing
     * window borders.
     */
    BORDERLESS("A borderless fullscreen which sets the width and height of the window to the monitor's video mode and removing window borders."),

    /**
     * Standard fullscreen mode of Minecraft which minimizes the window on focus loss.
     */
    NATIVE("Standard fullscreen mode of Minecraft which minimizes the window on focus loss."),

    /**
     * The same as {@link #NATIVE}, but without automatic iconify on focus loss of the window.
     * The window may be always on top, depending on the current {@link DesktopEnvironment}.
     */
    NATIVE_ON_FOCUS_LOSS_NO_ICONIFY("The same as NATIVE, but doesn't minimize the window on focus loss. Note: The window may be always on top, depending on the operating system."),

    /**
     * The same as {@link #NATIVE}, but switches to normal windowed mode on focus loss of the window.
     */
    NATIVE_ON_FOCUS_LOSS_WINDOWED("The same as NATIVE, but switches to a normal windowed mode on focus loss. When focus is gained again, switches back to fullscreen."),

    ;

    private final String comment;

    FullscreenModeConfig(@Nonnull String comment) {
        this.comment = comment;
    }

    /**
     * A description comment for this option.
     *
     * @return A comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Converts this config option into a {@link FullscreenMode}.
     *
     * @return The fullscreen mode
     */
    public FullscreenMode newFullscreenMode() {
        switch (this) {
            case DEFAULT:
                return FullscreenMode.getDefault(DesktopEnvironment.get());
            case BORDERLESS:
                return new BorderlessFullscreen();
            case NATIVE_ON_FOCUS_LOSS_NO_ICONIFY:
                return new NativeNonIconifyFullscreen();
            case NATIVE_ON_FOCUS_LOSS_WINDOWED:
                return new NativeWindowedFullscreen();
            case NATIVE:
            default:
                return new NativeFullscreen();
        }
    }

}
