package de.nekeras.borderless.client.fullscreen;

import de.nekeras.borderless.client.GlfwUtils;
import de.nekeras.borderless.client.GlfwWindowAttribute;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

/**
 * The same as {@link NativeFullscreenDisplay}, but switches to normal windowed mode on focus loss of
 * the window.
 */
@OnlyIn(Dist.CLIENT)
public class NativeWindowedFullscreenDisplay implements FullscreenDisplayMode {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void apply(@Nonnull MainWindow window) {
        FullscreenDisplayMode.super.apply(window);

        GlfwUtils.disableWindowAttribute(window, GlfwWindowAttribute.AUTO_ICONIFY);
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
    public void reset(@Nonnull MainWindow window) {
        FullscreenDisplayMode.super.reset(window);

        GLFW.glfwSetWindowFocusCallback(window.getWindow(), null);
    }

    private void onFocusGained(@Nonnull MainWindow window) {
        Monitor monitor = GlfwUtils.tryGetMonitor(window).orElse(null);

        if (monitor == null) {
            log.error("Could not find monitor for window");
        } else {
            VideoMode videoMode = window.getPreferredFullscreenVideoMode().orElseGet(monitor::getCurrentMode);

            GLFW.glfwSetWindowMonitor(window.getWindow(), monitor.getMonitor(), 0, 0,
                    videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate());
        }
    }

    private void onFocusLost(@Nonnull MainWindow window) {
        GLFW.glfwSetWindowMonitor(window.getWindow(), 0,
                window.getX(), window.getY(),
                window.getWidth(), window.getHeight(), GLFW.GLFW_DONT_CARE);
    }
}
