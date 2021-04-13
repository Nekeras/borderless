package de.nekeras.borderless;

import de.nekeras.borderless.fullscreen.FullscreenMode;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.IWindowEventListener;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * A custom {@link IWindowEventListener} that will call all original methods of the supplied
 * default event listener. In addition, this method will apply or reset the current fullscreen mode,
 * once {@link MainWindow#isFullscreen()} returns <code>true</code> or <code>false</code>
 * respectively.
 *
 * @see FullscreenModeHolder#getFullscreenMode()
 * @see FullscreenMode#apply(MainWindow)
 * @see FullscreenMode#reset(MainWindow)
 */
public class FullscreenWindowEventListener implements IWindowEventListener {

    private final IWindowEventListener defaultWindowEventListener;

    public FullscreenWindowEventListener(@Nonnull IWindowEventListener defaultWindowEventListener) {
        this.defaultWindowEventListener = Objects.requireNonNull(defaultWindowEventListener);
    }

    @Override
    public void setWindowActive(boolean focused) {
        defaultWindowEventListener.setWindowActive(focused);
    }

    @Override
    public void resizeDisplay() {
        defaultWindowEventListener.resizeDisplay();

        if (ReflectionUtil.isCalledByGlfwCallback()) {
            return;
        }

        FullscreenModeHolder.forceFullscreenModeUpdate();
    }

    @Override
    public void cursorEntered() {
        defaultWindowEventListener.cursorEntered();
    }
}
