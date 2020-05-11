package de.nekeras.borderless;

import de.nekeras.borderless.fullscreen.NativeFullscreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.Platform;

/**
 * The desktop environment we are running on, retrievable by {@link #get()}.
 */
public enum DesktopEnvironment {

    /**
     * The Windows desktop environment.
     */
    WINDOWS,

    /**
     * X11 window system used by Linux distributions.
     */
    X11,

    /**
     * An unknown desktop environment, this should always use {@link NativeFullscreen}.
     */
    GENERIC;

    private static final String LINUX_WINDOW_SYSTEM_VARIABLE = "XDG_SESSION_TYPE";
    private static final Logger LOG = LogManager.getLogger();
    private static final DesktopEnvironment CURRENT;

    static {
        LOG.info("Determining desktop environment");

        DesktopEnvironment current = null;

        switch (Platform.get()) {
            case WINDOWS:
                current = WINDOWS;
                break;
            case LINUX:
                String result = System.getenv(LINUX_WINDOW_SYSTEM_VARIABLE);

                if ("x11".equalsIgnoreCase(result)) {
                    current = X11;
                } else {
                    // Also treats Wayland as unknown as this seems buggy with LWJGL
                    current = GENERIC;
                    LOG.warn("Unknown window system: {}", result);
                }

                break;
            case MACOSX:
                current = GENERIC;
                break;
        }

        CURRENT = current;
        LOG.info("Found desktop environment {}", CURRENT);
    }

    /**
     * The current desktop environment Minecraft is running on.
     *
     * @return The desktop environment
     */
    public static DesktopEnvironment get() {
        return CURRENT;
    }

}
