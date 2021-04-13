package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * The same as {@link NativeFullscreen}, but switches to normal windowed mode on focus loss of
 * the window.
 */
public class NativeWindowedFullscreen implements FullscreenMode {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void apply(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getWindow(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowFocusCallback(window.getWindow(), (win, focused) -> {
            if (focused) {
                onFocusGained(window);
            } else {
                onFocusLost(window);
            }
        });
    }

    @Override
    public void reset(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getWindow(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
        GLFW.glfwSetWindowFocusCallback(window.getWindow(), null);
    }

    private void onFocusGained(MainWindow window) {
        Monitor monitor = window.findBestMonitor();

        if (monitor == null) {
            log.error("Could not find monitor for window");
            return;
        }

        VideoMode videoMode = window.getPreferredFullscreenVideoMode().orElseGet(monitor::getCurrentMode);

        GLFW.glfwSetWindowMonitor(window.getWindow(), monitor.getMonitor(), 0, 0,
                videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate());
    }

    private void onFocusLost(MainWindow window) {
        GLFW.glfwSetWindowMonitor(window.getWindow(), 0,
                window.getX(), window.getY(),
                window.getWidth(), window.getHeight(), GLFW.GLFW_DONT_CARE);
    }

}
