package de.nekeras.borderless.client.fullscreen;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.client.GlfwUtils;
import de.nekeras.borderless.client.GlfwWindowAttribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

/**
 * Switches into a windowed mode and removes the borders of the window. After that, maximizes the
 * window on the current monitor as returned by {@link MainWindow#findBestMonitor()}.
 */
@OnlyIn(Dist.CLIENT)
public class BorderlessFullscreenDisplay implements FullscreenDisplayMode {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void apply(@Nonnull Window window) {
        FullscreenDisplayMode.super.apply(window);

        Monitor monitor = GlfwUtils.tryGetMonitor(window).orElse(null);

        if (monitor == null) {
            log.error("Window's monitor could not be retrieved");
        } else {
            VideoMode videoMode = monitor.getCurrentMode();
            String name = GlfwUtils.getMonitorName(monitor);
            int x = monitor.getX();
            int y = monitor.getY();
            int width = videoMode.getWidth();
            int height = videoMode.getHeight();

            log.info("Apply on monitor {} at ({}|{}) size ({} x {})", name, x, y, width, height);

            GlfwUtils.disableWindowAttribute(window, GlfwWindowAttribute.DECORATED);
            GLFW.glfwSetWindowMonitor(window.getWindow(), 0, x, y, width, height, GLFW.GLFW_DONT_CARE);
        }
    }

    @Override
    public void reset(@Nonnull Window window) {
        FullscreenDisplayMode.super.reset(window);

        if (window.isFullscreen()) {
            Monitor monitor = GlfwUtils.tryGetMonitor(window).orElse(null);

            if (monitor == null) {
                log.error("Window's monitor could not be retrieved");
            } else {
                VideoMode videoMode = monitor.getCurrentMode();
                String name = GlfwUtils.getMonitorName(monitor);
                int width = videoMode.getWidth();
                int height = videoMode.getHeight();

                log.info("Reset on monitor {} at size ({} x {})", name, width, height);

                GLFW.glfwSetWindowMonitor(window.getWindow(), monitor.getMonitor(), 0, 0, width, height, GLFW.GLFW_DONT_CARE);
            }
        }
    }
}
