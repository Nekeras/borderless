package de.nekeras.borderless;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.terminal.impl.jna.osx.CLibrary.winsize;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IWindowEventListener;

/**
 * A custom {@link IWindowEventListener} that will call all original methods of the supplied
 * default event listener. In addition, this method will enter a borderless fullscreen or leave it,
 * once {@link MainWindow#isFullscreen()} returns <code>true</code> or <code>false</code>
 * respectively.
 *
 * @see Borderless#enterBorderlessFullscreen(MainWindow)
 * @see Borderless#leaveBorderlessFullscreen(MainWindow)
 */
public class BorderlessWindowEventListener implements IWindowEventListener {

    private static final Logger log = LogManager.getLogger();

    private final IWindowEventListener defaultWindowEventListener;

    public BorderlessWindowEventListener(@Nonnull IWindowEventListener defaultWindowEventListener) {
        this.defaultWindowEventListener = Objects.requireNonNull(defaultWindowEventListener);
    }

    @Override
    public void setGameFocused(boolean focused) {
        defaultWindowEventListener.setGameFocused(focused);
    }

    @Override
    public void updateDisplay(boolean limitFramerate) {
        defaultWindowEventListener.updateDisplay(limitFramerate);
    }

    @Override
    public void updateWindowSize() {
        defaultWindowEventListener.updateWindowSize();

        if (ReflectionUtil.isCalledByGlfwCallback()) {
            return;
        }

        MainWindow window = Minecraft.getInstance().mainWindow;

        if (window == null) {
            log.warn("Window is null, this is probably a bug");
        } else {
            log.info("Window fullscreen state: {}", window.isFullscreen());
            log.info("In borderless fullscreen: {}", Borderless.isInBorderlessFullscreen());

            if (window.isFullscreen() != Borderless.isInBorderlessFullscreen()) {
                if (window.isFullscreen()) {
                    Borderless.enterBorderlessFullscreen(window);
                } else {
                    Borderless.leaveBorderlessFullscreen(window);
                }
            }
        }
    }

}
