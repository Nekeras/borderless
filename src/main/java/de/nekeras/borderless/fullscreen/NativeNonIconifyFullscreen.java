package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
public class NativeNonIconifyFullscreen implements FullscreenMode {

    @Override
    public void apply(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
    }

    @Override
    public void reset(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
    }

}
