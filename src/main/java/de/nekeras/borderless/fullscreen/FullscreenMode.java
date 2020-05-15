package de.nekeras.borderless.fullscreen;

import de.nekeras.borderless.Borderless;
import de.nekeras.borderless.DesktopEnvironment;
import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.IWindowEventListener;

/**
 * A fullscreen mode that can be applied for the Minecraft {@link MainWindow}. The {@link
 * #apply(MainWindow)} method
 * will be called every time the window enters fullscreen, the {@link #reset(MainWindow)} method
 * every time the window
 * leaves fullscreen. {@link #apply(MainWindow)} will only be called once {@link
 * #shouldApply(MainWindow)} returns
 * <code>true</code>, {@link #reset(MainWindow)} will only be called once {@link
 * #shouldReset(MainWindow)} returns
 * <code>true</code>.
 */
public interface FullscreenMode {

    /**
     * Switches into a windowed mode and removes the borders of the window. After that, maximizes
     * the window on the current monitor as returned by {@link MainWindow#getMonitor()}.
     */
    FullscreenMode BORDERLESS = new BorderlessFullscreen();

    /**
     * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this
     * mode will not affect the {@link MainWindow}.
     */
    FullscreenMode NATIVE = new NativeFullscreen();

    /**
     * The native fullscreen mode, but without automatic iconify on focus loss of the window.
     */
    FullscreenMode NATIVE_NON_ICONIFY = new NativeNonIconifyFullscreen();

    /**
     * The same as {@link NativeFullscreen}, but switches to normal windowed mode on focus loss of
     * the window.
     */
    FullscreenMode NATIVE_WINDOWED = new NativeWindowedFullscreen();

    /**
     * Enters a window into this fullscreen mode. This method should only
     * be called by the {@link IWindowEventListener} callback. The fullscreen of a
     * minecraft window should only be changed with {@link MainWindow#toggleFullscreen()}.
     *
     * @param window
     *     The window to switch into this mode
     */
    void apply(MainWindow window);

    /**
     * Leaves this fullscreen mode. This method should only
     * be called by the {@link IWindowEventListener} callback. The fullscreen of a
     * minecraft window should only be changed with {@link MainWindow#toggleFullscreen()}.
     *
     * @param window
     *     The window to switch into a normal mode
     */
    void reset(MainWindow window);

    /**
     * Whether this fullscreen mode has to {@link #apply(MainWindow)} on a window or not.<br>
     * The default behaviour returns <code>true</code> every time the window is in
     * {@link Borderless#isInNativeFullscreen(MainWindow) native fullscreen}.
     *
     * @param window
     *     The window this mode checks whether to apply on
     * @return <code>true</code> if the window is not in this fullscreen mode, otherwise <code>false</code>
     */
    default boolean shouldApply(MainWindow window) {
        return Borderless.isInNativeFullscreen(window);
    }

    /**
     * Whether this fullscreen mode has to {@link #reset(MainWindow)} on a window or not.<br>
     * The default behaviour returns <code>true</code> every time {@link MainWindow#isFullscreen()}
     * returns <code>false</code>.
     *
     * @param window
     *     The window this mode checks whether to apply on
     * @return <code>true</code> to reset this mode, otherwise <code>false</code>
     */
    default boolean shouldReset(MainWindow window) {
        return !window.isFullscreen();
    }

    /**
     * Gets the default fullscreen mode for the specified operating system.
     *
     * @param environment
     *     The desktop environment of the operating system
     * @return The fullscreen mode
     */
    static FullscreenMode getDefault(@Nonnull DesktopEnvironment environment) {
        switch (environment) {
            case WINDOWS:
                return BORDERLESS;
            case X11:
                return new NativeNonIconifyFullscreen();
            case GENERIC:
            default:
                return new NativeFullscreen();
        }
    }

    /**
     * Gets the current {@link FullscreenMode} as configured in the {@link Config} class.
     *
     * @return The fullscreen mode
     */
    static FullscreenMode fromConfig() {
        Config.General config = Config.GENERAL;
        FullscreenModeConfig mode = config.fullscreenMode.get();
        FocusLossConfig focusLoss = config.focusLoss.get();

        return fromConfig(mode, focusLoss);
    }

    /**
     * Gets the {@link FullscreenMode} from a custom configuration.
     *
     * @param mode
     *     The desired fullscreen mode
     * @param focusLoss
     *     The behaviour on focus loss of the window, this is only applied if the
     *     mode is {@link FullscreenModeConfig#NATIVE}.
     * @return The fullscreen mode
     */
    static FullscreenMode fromConfig(@Nonnull FullscreenModeConfig mode,
        @Nullable FocusLossConfig focusLoss) {

        switch (mode) {
            case BEST:
                return getDefault(DesktopEnvironment.get());
            case BORDERLESS:
                return BORDERLESS;
            case NATIVE:
                if (focusLoss != null) {
                    switch (focusLoss) {
                        case DO_NOTHING:
                            return NATIVE_NON_ICONIFY;
                        case MINIMIZE:
                            return NATIVE;
                        case SWITCH_TO_WINDOWED:
                            return NATIVE_WINDOWED;
                    }
                }
        }

        return NATIVE;
    }

}
