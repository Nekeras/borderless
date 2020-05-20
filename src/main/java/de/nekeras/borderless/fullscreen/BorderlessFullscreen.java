package de.nekeras.borderless.fullscreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;

/**
 * Switches into a windowed mode and removes the borders of the window. After that, maximizes the
 * window on the current monitor as returned by {@link MainWindow#getMonitor()}.
 */
public class BorderlessFullscreen implements FullscreenMode {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void apply(MainWindow window) {
        Monitor monitor = window.getMonitor();

        if (monitor == null) {
            log.error("Window's monitor could not be retrieved");
            return;
        }

        VideoMode videoMode = monitor.getDefaultVideoMode();

        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowMonitor(window.getHandle(), 0, monitor.getVirtualPosX(),
            monitor.getVirtualPosY(), videoMode.getWidth(), videoMode.getHeight(),
            GLFW.GLFW_DONT_CARE);
    }

    @Override
    public void reset(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
    }

}
