package de.nekeras.borderless.client.gui;

import de.nekeras.borderless.util.Translatable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class EnumOption<E extends Enum<E> & Translatable> extends AbstractOption {

    private static final int BUTTON_HEIGHT = 20;

    private final E[] enumConstants;
    private final Function<GameSettings, E> getter;
    private final BiConsumer<GameSettings, E> setter;

    public EnumOption(@Nonnull String translationKey, @Nonnull E[] enumConstants,
                      @Nonnull Function<GameSettings, E> getter, @Nonnull BiConsumer<GameSettings, E> setter) {
        super(translationKey);
        this.enumConstants = enumConstants;
        this.getter = getter;
        this.setter = setter;
    }

    @Nonnull
    public E getValue(@Nonnull GameSettings settings) {
        return getter.apply(settings);
    }

    @Nonnull
    public ITextComponent getMessage(@Nonnull GameSettings settings) {
        return genericValueLabel(getValue(settings).getTranslation());
    }

    @Nonnull
    @Override
    public Widget createButton(@Nonnull GameSettings settings, int x, int y, int width) {
        return new OptionButton(x, y, width, BUTTON_HEIGHT, this, getMessage(settings), btn -> {
            onButtonClick(settings, btn);
        });
    }

    private void onButtonClick(@Nonnull GameSettings settings, @Nonnull Button button) {
        E value = getValue(settings);
        int nextValueIndex = (value.ordinal() + 1) % enumConstants.length;
        E nextValue = enumConstants[nextValueIndex];

        setter.accept(settings, nextValue);
        button.setMessage(getMessage(settings));
    }
}
