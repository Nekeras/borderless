package de.nekeras.borderless;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nekeras.borderless.fullscreen.FullscreenMode;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IWindowEventListener;

/**
 * A custom {@link IWindowEventListener} that will call all original methods of the supplied
 * default event listener. In addition, this method will apply or reset the current fullscreen mode,
 * once {@link MainWindow#isFullscreen()} returns <code>true</code> or <code>false</code>
 * respectively.
 *
 * @see Borderless#getFullscreenMode()
 * @see FullscreenMode#apply(MainWindow)
 * @see FullscreenMode#reset(MainWindow)
 */
public class FullscreenWindowEventListener implements IWindowEventListener {

    private static final Logger LOG = LogManager.getLogger();

    private final IWindowEventListener defaultWindowEventListener;

    public FullscreenWindowEventListener(@Nonnull IWindowEventListener defaultWindowEventListener) {
        this.defaultWindowEventListener = Objects.requireNonNull(defaultWindowEventListener);
    }

    @Override
    public void setGameFocused(boolean focused) {
        defaultWindowEventListener.setGameFocused(focused);
    }

    @Override
    public void updateWindowSize() {
        defaultWindowEventListener.updateWindowSize();

        if (ReflectionUtil.isCalledByGlfwCallback()) {
            return;
        }

        MainWindow window = Minecraft.getInstance().getMainWindow();
        FullscreenMode fullscreenMode = Borderless.getFullscreenMode();

        LOG.info("Window fullscreen state: {}", window.isFullscreen());
        LOG.info("In native fullscreen: {}", Borderless.isInNativeFullscreen(window));

        if (fullscreenMode == null) {
            LOG.error("Unexpected null value for fullscreen mode");
            return;
        }

        if (fullscreenMode.shouldApply(window)) {
            fullscreenMode.apply(window);
        }

        if (fullscreenMode.shouldReset(window)) {
            fullscreenMode.reset(window);
        }
    }

}
