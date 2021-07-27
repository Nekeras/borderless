package de.nekeras.borderless.client.gui;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButtonOption extends AbstractOption {

    private static final int BUTTON_HEIGHT = 20;

    private final Button.IPressable onClick;

    public ButtonOption(@Nonnull String title, @Nonnull Button.IPressable onClick) {
        super(title);
        this.onClick = onClick;
    }

    @Nonnull
    @Override
    public Widget createButton(@Nonnull GameSettings settings, int x, int y, int width) {
        return new OptionButton(x, y, width, BUTTON_HEIGHT, this, getCaption(), onClick);
    }
}
