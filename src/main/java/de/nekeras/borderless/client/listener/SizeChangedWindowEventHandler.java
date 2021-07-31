package de.nekeras.borderless.client.listener;

import com.mojang.blaze3d.platform.WindowEventHandler;
import de.nekeras.borderless.client.ReflectionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A custom {@link WindowEventHandler} that will call all original methods of the supplied
 * default event listener. Also, this method will run a callback supplied in the constructor whenever the
 * window's size has changed (i.e., every time {@link #resizeDisplay()} is called by a non-GLFW callback).
 */
@OnlyIn(Dist.CLIENT)
public class SizeChangedWindowEventHandler implements WindowEventHandler {

    private static final Logger log = LoggerFactory.getLogger(SizeChangedWindowEventHandler.class);

    private final WindowEventHandler defaultWindowEventListener;
    private final Runnable onDisplayResize;

    private boolean focused = false;

    public SizeChangedWindowEventHandler(@Nullable WindowEventHandler defaultWindowEventListener, @Nonnull Runnable onDisplayResize) {
        this.defaultWindowEventListener = defaultWindowEventListener;
        this.onDisplayResize = onDisplayResize;
    }

    @Override
    public void setWindowActive(boolean focused) {
        this.focused = focused;

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

        log.info("Window focused: {}", focused);
        onDisplayResize.run();
    }

    @Override
    public void cursorEntered() {
        if (defaultWindowEventListener != null) {
            defaultWindowEventListener.cursorEntered();
        }
    }
}
