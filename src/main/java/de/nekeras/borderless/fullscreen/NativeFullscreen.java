package de.nekeras.borderless.fullscreen;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.MainWindow;

/**
 * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this mode
 * will not affect the {@link MainWindow}.
 */
public class NativeFullscreen implements FullscreenMode {

    @Override
    public void apply(MainWindow window) {
        // Ensure GLFW_AUTO_ICONIFY is enabled to be consistent with standard behaviour
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
    }

    @Override
    public void reset(MainWindow window) {
        // Nothing to do for the native fullscreen
    }

}
