package de.nekeras.borderless.client.gui;

import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButtonOption extends Option {

    private static final int BUTTON_HEIGHT = 20;

    private final Button.OnPress onClick;

    public ButtonOption(@Nonnull String title, @Nonnull Button.OnPress onClick) {
        super(title);
        this.onClick = onClick;
    }

    @Nonnull
    @Override
    public AbstractWidget createButton(@Nonnull Options options, int x, int y, int width) {
        return new Button(x, y, width, BUTTON_HEIGHT, getCaption(), onClick);
    }
}
