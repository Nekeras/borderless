package de.nekeras.borderless.client.fullscreen;

import com.mojang.blaze3d.platform.Window;
import de.nekeras.borderless.client.GlfwUtils;
import de.nekeras.borderless.client.GlfwWindowAttribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * The native fullscreen mode, but without automatic iconify on focus loss of the window.
 */
@OnlyIn(Dist.CLIENT)
public class NativeNonIconifyFullscreenDisplay implements FullscreenDisplayMode {

    @Override
    public void apply(@Nonnull Window window) {
        FullscreenDisplayMode.super.apply(window);

        GlfwUtils.disableWindowAttribute(window, GlfwWindowAttribute.AUTO_ICONIFY);
    }
}
