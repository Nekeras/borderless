package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * Switches into a windowed mode and removes the borders of the window. After that, maximizes the
 * window on the current monitor as returned by {@link MainWindow#findBestMonitor()}.
 */
public class BorderlessFullscreen implements FullscreenMode {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void apply(MainWindow window) {
        Monitor monitor = window.findBestMonitor();

        if (monitor == null) {
            log.error("Window's monitor could not be retrieved");
            return;
        }

        VideoMode videoMode = monitor.getCurrentMode();

        GLFW.glfwSetWindowAttrib(window.getWindow(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowMonitor(window.getWindow(), 0, monitor.getX(),
                monitor.getY(), videoMode.getWidth(), videoMode.getHeight(),
                GLFW.GLFW_DONT_CARE);
    }

    @Override
    public void reset(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getWindow(), GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
    }

}
