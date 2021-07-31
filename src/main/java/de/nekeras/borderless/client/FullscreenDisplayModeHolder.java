package de.nekeras.borderless.client;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.client.fullscreen.FullscreenDisplayMode;
import de.nekeras.borderless.client.listener.SizeChangedWindowEventHandler;
import de.nekeras.borderless.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

/**
 * Singleton class holding the current fullscreen display mode.
 */
@OnlyIn(Dist.CLIENT)
public class FullscreenDisplayModeHolder {

    private static final Logger log = LogManager.getLogger();
    private static final Window window = Minecraft.getInstance().getWindow();
    private static FullscreenDisplayMode currentMode;

    /**
     * Initializes the Minecraft environment to make it compatible with this mod.
     */
    public static void initMinecraft() {
        log.info("Overwriting Minecraft WindowEventListener");
        ReflectionUtils.updateWindowEventListener(window, oldListener -> new SizeChangedWindowEventHandler(
                oldListener, FullscreenDisplayModeHolder::setFullscreenDisplayModeFromConfig));

        log.info("Overwrite finished");
        FullscreenDisplayModeHolder.setFullscreenDisplayModeFromConfig();
    }

    /**
     * The fullscreen mode that is applied instead of the native fullscreen once the user hits
     * F11 or switches to fullscreen in the video settings.
     *
     * @param newMode The fullscreen mode
     */
    public static void setFullscreenDisplayMode(@Nullable FullscreenDisplayMode newMode) {
        log.info("Detected fullscreen mode change from {} to {}", currentMode, newMode);

        if (currentMode != null) {
            currentMode.reset(window);
        }

        log.info("Refreshing {}", newMode);

        if (newMode != null) {
            if (window.isFullscreen()) {
                newMode.apply(window);
            } else {
                newMode.reset(window);
            }
        }

        currentMode = newMode;

        GlfwUtils.checkInputMode(window);
    }

    /**
     * Re-applies the current fullscreen display mode set in the {@link de.nekeras.borderless.config.Config}.
     */
    public static void setFullscreenDisplayModeFromConfig() {
        FullscreenDisplayMode configMode = Config.getFullscreenDisplayMode();
        log.info("Refreshing fullscreen mode from config to {}", configMode);
        setFullscreenDisplayMode(configMode);
    }
}
