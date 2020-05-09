package de.nekeras.borderless.fullscreen;

import de.nekeras.borderless.Borderless;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.IWindowEventListener;

public interface FullscreenMode {

    /**
     * A {@link BorderlessFullscreen} in a windowed mode.
     */
    BorderlessFullscreen BORDERLESS = new BorderlessFullscreen();

    /**
     * A {@link NativeFullscreen} as supported natively by Minecraft, but with disabled
     * {@link org.lwjgl.glfw.GLFW#GLFW_AUTO_ICONIFY}.
     */
    NativeNonIconfiyFullscreen NATIVE_NON_ICONFIY = new NativeNonIconfiyFullscreen();

    /**
     * A {@link NativeFullscreen} as supported natively by Minecraft.
     */
    NativeFullscreen NATIVE = new NativeFullscreen();

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

}
