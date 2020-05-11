package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;

/**
 * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this mode
 * will not affect the {@link MainWindow}.
 */
public class NativeFullscreen implements FullscreenMode {

    @Override
    public void apply(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
    }

    @Override
    public void reset(MainWindow window) {
    }

    @Override
    public boolean shouldApply(MainWindow window) {
        return GLFW.glfwGetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY) != GLFW.GLFW_TRUE;
    }

    @Override
    public boolean shouldReset(MainWindow window) {
        return false;
    }

}
