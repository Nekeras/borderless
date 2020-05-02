package de.nekeras.borderless.fullscreen;

import net.minecraft.client.MainWindow;

/**
 * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this mode
 * will not affect the {@link MainWindow}.
 */
public class NativeFullscreen implements FullscreenMode {

    @Override
    public void apply(MainWindow window) {
    }

    @Override
    public void reset(MainWindow window) {
    }

    @Override
    public boolean shouldApply(MainWindow window) {
        return false;
    }

    @Override
    public boolean shouldReset(MainWindow window) {
        return false;
    }

}
