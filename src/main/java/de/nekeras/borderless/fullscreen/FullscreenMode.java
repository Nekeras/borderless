package de.nekeras.borderless.fullscreen;

import de.nekeras.borderless.Borderless;
import de.nekeras.borderless.DesktopEnvironment;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.IWindowEventListener;

/**
 * A fullscreen mode that can be applied for the Minecraft {@link MainWindow}. The {@link #apply(MainWindow)} method
 * will be called every time the window enters fullscreen, the {@link #reset(MainWindow)} method every time the window
 * leaves fullscreen. {@link #apply(MainWindow)} will only be called once {@link #shouldApply(MainWindow)} returns
 * <code>true</code>, {@link #reset(MainWindow)} will only be called once {@link #shouldReset(MainWindow)} returns
 * <code>true</code>.
 */
public interface FullscreenMode {

    /**
     * Enters a window into this fullscreen mode. This method should only
     * be called by the {@link IWindowEventListener} callback. The fullscreen of a
     * minecraft window should only be changed with {@link MainWindow#toggleFullscreen()}.
     *
     * @param window The window to switch into this mode
     */
    void apply(MainWindow window);

    /**
     * Leaves this fullscreen mode. This method should only
     * be called by the {@link IWindowEventListener} callback. The fullscreen of a
     * minecraft window should only be changed with {@link MainWindow#toggleFullscreen()}.
     *
     * @param window The window to switch into a normal mode
     */
    void reset(MainWindow window);

    /**
     * Whether this fullscreen mode has to {@link #apply(MainWindow)} on a window or not.<br>
     * The default behaviour returns <code>true</code> every time the window is in
     * {@link Borderless#isInNativeFullscreen(MainWindow) native fullscreen}.
     *
     * @param window The window this mode checks whether to apply on
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
     * @param window The window this mode checks whether to apply on
     * @return <code>true</code> to reset this mode, otherwise <code>false</code>
     */
    default boolean shouldReset(MainWindow window) {
        return !window.isFullscreen();
    }

    /**
     * Gets the default fullscreen mode for the specified operating system.
     *
     * @param environment The desktop environment of the operating system
     * @return The fullscreen mode
     */
    static FullscreenMode getDefault(DesktopEnvironment environment) {
        switch (environment) {
            case WINDOWS:
                return new BorderlessFullscreen();
            case X11:
                return new NativeNonIconifyFullscreen();
            case GENERIC:
            default:
                return new NativeFullscreen();
        }
    }

}
