package de.nekeras.borderless;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.IWindowEventListener;
import net.minecraft.client.renderer.VideoMode;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(Borderless.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class Borderless {

    public static final String MOD_ID = "borderless";

    private static final Logger log = LogManager.getLogger();

    private static boolean fullscreenState;

    public Borderless() {
        // Client dist only, make sure server is always compatible with this mod
        ModLoadingContext.get().registerExtensionPoint(
            ExtensionPoint.DISPLAYTEST,
            () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e) {
        Minecraft minecraft = Minecraft.getInstance();
        MainWindow window = minecraft.mainWindow;
        ReflectionUtil.updateWindowEventListener(window, BorderlessWindowEventListener::new);
    }

    /**
     * Checks whether the {@link MainWindow} is currently in borderless windowed fullscreen. This
     * may return different results than {@link MainWindow#isFullscreen()}, i.e. if the fullscreen
     * was not set by {@link #enterBorderlessFullscreen(MainWindow)}.
     *
     * @return <code>true</code> if the window is currently in borderless fullscreen, otherwise
     * <code>false</code>
     */
    public static boolean isInBorderlessFullscreen() {
        return fullscreenState;
    }

    /**
     * Enters the {@link MainWindow} in a borderless windowed fullscreen. This method should only
     * be called by the {@link IWindowEventListener} callback. The fullscreen of a
     * minecraft window should only be changed with {@link MainWindow#toggleFullscreen()}.
     *
     * @param window The window to enter fullscreen on
     * @see BorderlessWindowEventListener
     */
    public static void enterBorderlessFullscreen(MainWindow window) {
        fullscreenState = true;
        Monitor monitor = window.func_224796_s();

        if (monitor == null) {
            return;
        }

        VideoMode videoMode = monitor.getDefaultVideoMode();

        log.info("Entering fullscreen on monitor 0x{} with resolution {}x{} on position {}|{}",
            Long.toHexString(monitor.getMonitorPointer()),
            videoMode.getWidth(), videoMode.getHeight(),
            monitor.getVirtualPosX(), monitor.getVirtualPosY());

        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
        GLFW.glfwSetWindowMonitor(window.getHandle(), 0, monitor.getVirtualPosX(),
            monitor.getVirtualPosY(), videoMode.getWidth(), videoMode.getHeight(),
            GLFW.GLFW_DONT_CARE);
    }

    /**
     * Leaves the {@link MainWindow} in a borderless windowed fullscreen. This method should only
     * be called by the {@link IWindowEventListener} callback. The fullscreen of a
     * minecraft window should only be changed with {@link MainWindow#toggleFullscreen()}.
     *
     * @param window The window to enter fullscreen on
     * @see BorderlessWindowEventListener
     */
    public static void leaveBorderlessFullscreen(MainWindow window) {
        fullscreenState = false;
        log.info("Leaving fullscreen and resetting window size");

        GLFW.glfwSetWindowAttrib(window.getHandle(), GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        GLFW.glfwSetWindowSize(window.getHandle(), window.getWidth(), window.getHeight());
    }

}
