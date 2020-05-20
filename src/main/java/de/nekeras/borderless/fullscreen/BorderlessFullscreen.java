package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * Switches into a windowed mode and removes the borders of the window. After that, maximizes the
 * window on the current monitor as returned by {@link MainWindow#getMonitor()}.
 */
public class BorderlessFullscreen implements FullscreenMode {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void apply(MainWindow window) {
        Monitor monitor = window.getMonitor();

        if (monitor == null) {
            LOG.error("Window's monitor could not be retrieved");
            return;
        }

        VideoMode videoMode = monitor.getDefaultVideoMode();

        LOG.info(
            "Entering borderless fullscreen on monitor 0x{} with resolution {}x{} at position {}|{}",
            Long.toHexString(monitor.getMonitorPointer()),
            videoMode.getWidth(), videoMode.getHeight(),
            monitor.getVirtualPosX(), monitor.getVirtualPosY());

        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowMonitor(window.getHandle(), 0, monitor.getVirtualPosX(),
            monitor.getVirtualPosY(), videoMode.getWidth(), videoMode.getHeight(),
            GLFW.GLFW_DONT_CARE);
    }

    @Override
    public void reset(MainWindow window) {
        LOG.info("Leaving borderless fullscreen and resetting window size");

        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
    }

}
