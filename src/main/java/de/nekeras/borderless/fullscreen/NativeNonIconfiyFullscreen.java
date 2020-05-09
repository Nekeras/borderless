package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;

public class NativeNonIconfiyFullscreen implements FullscreenMode {

    @Override
    public void apply(MainWindow window) {
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
    }

    @Override
    public void reset(MainWindow window) {
    }

    @Override
    public boolean shouldApply(MainWindow window) {
        return GLFW.glfwGetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY) != GLFW.GLFW_FALSE;
    }

    @Override
    public boolean shouldReset(MainWindow window) {
        return false;
    }
}
