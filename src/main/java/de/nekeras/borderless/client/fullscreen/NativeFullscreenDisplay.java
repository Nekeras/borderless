package de.nekeras.borderless.client.fullscreen;

import net.minecraft.client.MainWindow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The native fullscreen mode, this fullscreen mode can be used to disable this mod, as this mode
 * will not affect the {@link MainWindow}.
 */
@OnlyIn(Dist.CLIENT)
public class NativeFullscreenDisplay implements FullscreenDisplayMode {
}
