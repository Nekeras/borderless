package de.nekeras.borderless;

import java.util.Objects;

import javax.annotation.Nonnull;

import de.nekeras.borderless.fullscreen.FullscreenMode;
import net.minecraft.client.MainWindow;
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

        Borderless.forceFullscreenModeUpdate();
    }

    @Override
    public void func_241216_b_() {
        defaultWindowEventListener.func_241216_b_();
    }

}
