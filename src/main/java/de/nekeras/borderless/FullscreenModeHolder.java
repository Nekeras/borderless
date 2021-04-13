package de.nekeras.borderless;

import de.nekeras.borderless.fullscreen.FullscreenMode;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FullscreenModeHolder {

    private static final Logger log = LogManager.getLogger();
    private static FullscreenMode fullscreenMode;

    /**
     * Initializes the Minecraft environment to make it compatible with this mod.
     */
    public static void initMinecraft() {
        log.info("Reading fullscreen mode from config");
        fullscreenMode = FullscreenMode.fromConfig();
        log.info("Overwriting Minecraft WindowEventListener");
        Minecraft minecraft = Minecraft.getInstance();
        MainWindow window = minecraft.getWindow();
        ReflectionUtil.updateWindowEventListener(window, FullscreenWindowEventListener::new);
        log.info("Overwrite finished");
        FullscreenModeHolder.forceFullscreenModeUpdate();
    }

    /**
     * Checks whether the {@link MainWindow} is currently in native fullscreen. This
     * may return different results than {@link MainWindow#isFullscreen()}. The window is considered
     * in native fullscreen if {@link GLFW#glfwGetWindowMonitor(long)} returns non-null and
     * {@link MainWindow#isFullscreen()} returns <code>true</code>.
     *
     * @return <code>true</code> if the window is currently in native fullscreen, otherwise
     * <code>false</code>
     */
    public static boolean isInNativeFullscreen(@Nonnull MainWindow window) {
        return window.isFullscreen() && GLFW.glfwGetWindowMonitor(window.getWindow()) != 0;
    }

    /**
     * The fullscreen mode that is applied instead of the native fullscreen once the user hits
     * F11 or switches to fullscreen in the video settings.
     *
     * @return The fullscreen mode
     */
    public static FullscreenMode getFullscreenMode() {
        return fullscreenMode;
    }

    /**
     * The fullscreen mode that is applied instead of the native fullscreen once the user hits
     * F11 or switches to fullscreen in the video settings.
     *
     * @param fullscreenMode The fullscreen mode
     */
    public static void setFullscreenMode(@Nonnull FullscreenMode fullscreenMode) {
        Objects.requireNonNull(fullscreenMode);

        Minecraft minecraft = Minecraft.getInstance();
        MainWindow window = minecraft.getWindow();
        FullscreenMode currentFullscreenMode = getFullscreenMode();

        log.info("Resetting fullscreen mode '{}' - Window fullscreen: {}; Native fullscreen: {}",
                currentFullscreenMode == null ? null : currentFullscreenMode.getClass().getName(),
                window.isFullscreen(),
                isInNativeFullscreen(window));

        if (currentFullscreenMode != null) {
            currentFullscreenMode.reset(window);
        }

        if (window.isFullscreen()) {
            log.info("Applying fullscreen mode '{}'", fullscreenMode.getClass().getName());
            FullscreenModeHolder.fullscreenMode = fullscreenMode;
            fullscreenMode.apply(window);
        }
    }

    /**
     * Triggers an update for current {@link FullscreenMode}.
     */
    public static void forceFullscreenModeUpdate() {
        setFullscreenMode(getFullscreenMode());
    }
}
