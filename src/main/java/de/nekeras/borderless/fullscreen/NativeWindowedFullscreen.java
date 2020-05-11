package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * The same as {@link NativeFullscreen}, but switches to normal windowed mode on focus loss of the window.
 */
public class NativeWindowedFullscreen implements FullscreenMode {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void apply(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowFocusCallback(window.getHandle(), (win, focused) -> onFocusChanged(window, focused));
    }

    @Override
    public void reset(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
        GLFW.glfwSetWindowFocusCallback(window.getHandle(), null);
    }

    private void onFocusChanged(MainWindow window, boolean focused) {
        if (focused) {
            Monitor monitor = window.getMonitor();

            if (monitor == null) {
                LOG.error("Could not find monitor for window");
                return;
            }

            VideoMode videoMode = window.getVideoMode().orElseGet(monitor::getDefaultVideoMode);

            GLFW.glfwSetWindowMonitor(window.getHandle(), monitor.getMonitorPointer(), 0, 0,
                    videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate());
        } else {
            GLFW.glfwSetWindowMonitor(window.getHandle(), 0,
                    window.getWindowX(), window.getWindowY(),
                    window.getWidth(), window.getHeight(), GLFW.GLFW_DONT_CARE);
        }
    }

}
