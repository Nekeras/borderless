package de.nekeras.borderless.client;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.Window;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Helper class for access for various GLFW functions with logging support.
 */
@OnlyIn(Dist.CLIENT)
public final class GlfwUtils {

    private static final Logger log = LogManager.getLogger();

    private GlfwUtils() {
    }

    /**
     * Retrieves the monitor's name from GLFW.
     *
     * @param monitor The monitor
     * @return The monitor's name
     */
    @Nonnull
    public static String getMonitorName(@Nonnull Monitor monitor) {
        String name = GLFW.glfwGetMonitorName(monitor.getMonitor());

        if (name == null) {
            log.warn("Could not retrieve monitor name for {}", monitor.getMonitor());
            return "- ERROR -";
        } else {
            return name;
        }
    }

    /**
     * Tries to get the monitor for the window. If it could be found and is non-null, it will be returned. Otherwise an
     * error is printed.
     *
     * @param window The window
     * @return The monitor wrapped in an {@link Optional}.
     */
    @Nonnull
    public static Optional<Monitor> tryGetMonitor(@Nonnull Window window) {
        Monitor monitor = window.findBestMonitor();

        if (monitor == null) {
            log.error("Window's current monitor could not be retrieved");
        }

        return Optional.ofNullable(monitor);
    }

    /**
     * Enables a single window attribute.
     *
     * @param window    The window
     * @param attribute The attribute
     */
    public static void enableWindowAttribute(@Nonnull Window window, @Nonnull GlfwWindowAttribute attribute) {
        log.info("Enable window attribute {}", attribute.name());
        GLFW.glfwSetWindowAttrib(window.getWindow(), attribute.getBit(), GLFW.GLFW_TRUE);
    }

    /**
     * Disables a single window attribute.
     *
     * @param window    The window
     * @param attribute The attribute
     */
    public static void disableWindowAttribute(@Nonnull Window window, @Nonnull GlfwWindowAttribute attribute) {
        log.info("Disable window attribute {}", attribute.name());
        GLFW.glfwSetWindowAttrib(window.getWindow(), attribute.getBit(), GLFW.GLFW_FALSE);
    }

    /**
     * Restores all default window attributes that are supported by the {@link GlfwWindowAttribute} enum.
     *
     * @param window The window
     */
    public static void applyDefaultWindowAttributes(@Nonnull Window window) {
        log.info("Resetting window attributes");

        for (GlfwWindowAttribute attribute : GlfwWindowAttribute.values()) {
            if (attribute.isEnabledByDefault()) {
                enableWindowAttribute(window, attribute);
            } else {
                disableWindowAttribute(window, attribute);
            }
        }

        log.info("Done resetting window attributes");
    }

    /**
     * Prints information of the current input mode for debugging purposes.
     *
     * @param window The window to check
     */
    public static void checkInputMode(@Nonnull Window window) {
        log.info("Checking window input mode");

        switch (GLFW.glfwGetInputMode(window.getWindow(), GLFW.GLFW_CURSOR)) {
            case GLFW.GLFW_CURSOR_NORMAL -> log.info("Detected normal cursor mode");
            case GLFW.GLFW_CURSOR_HIDDEN -> {
                log.info("Detected hidden cursor mode");
                GLFW.glfwSetInputMode(window.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
            }
            case GLFW.GLFW_CURSOR_DISABLED -> log.info("Detected disabled cursor mode");
            default -> log.info("Unknown cursor mode");
        }

        log.info("Done checking window input mode");

        double[] xPos = new double[1];
        double[] yPos = new double[1];

        GLFW.glfwGetCursorPos(window.getWindow(), xPos, yPos);

        int x = (int) Math.floor(xPos[0]);
        int y = (int) Math.floor(yPos[0]);
        int winWidth = window.getWidth();
        int winHeight = window.getHeight();

        boolean inside = x >= 0 && x <= winWidth && y >= 0 && y <= winHeight;
        log.info("Cursor is at {}x{} (inside window: {})", x, y, inside);
        log.info("Done checking cursor position");
    }
}
