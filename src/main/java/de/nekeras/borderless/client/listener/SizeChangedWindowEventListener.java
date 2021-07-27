package de.nekeras.borderless.client.listener;

import de.nekeras.borderless.client.ReflectionUtils;
import net.minecraft.client.renderer.IWindowEventListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A custom {@link IWindowEventListener} that will call all original methods of the supplied
 * default event listener. In addition, this method will run a callback supplied in the constructor whenever the
 * window's size has changed (i.e. every time {@link #resizeDisplay()} is called by a non-GLFW callback).
 */
@OnlyIn(Dist.CLIENT)
public class SizeChangedWindowEventListener implements IWindowEventListener {

    private final IWindowEventListener defaultWindowEventListener;
    private final Runnable onDisplayResize;

    public SizeChangedWindowEventListener(@Nullable IWindowEventListener defaultWindowEventListener, @Nonnull Runnable onDisplayResize) {
        this.defaultWindowEventListener = defaultWindowEventListener;
        this.onDisplayResize = onDisplayResize;
    }

    @Override
    public void setWindowActive(boolean focused) {
        if (defaultWindowEventListener != null) {
            defaultWindowEventListener.setWindowActive(focused);
        }
    }

    @Override
    public void resizeDisplay() {
        if (defaultWindowEventListener != null) {
            defaultWindowEventListener.resizeDisplay();
        }

        if (ReflectionUtils.isCalledByGlfwCallback()) {
            return;
        }

        onDisplayResize.run();
    }

    @Override
    public void cursorEntered() {
        if (defaultWindowEventListener != null) {
            defaultWindowEventListener.cursorEntered();
        }
    }
}
