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
        log.info("Enable window attribute {}", attribute.name());
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
}
