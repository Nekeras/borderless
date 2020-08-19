package de.nekeras.borderless.config.gui;

import javax.annotation.Nonnull;

import de.nekeras.borderless.config.value.ValueHolder;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EnumOption<E extends Enum<E>> extends AbstractOption {

    private static final int BUTTON_HEIGHT = 20;

    private final Class<E> enumClass;
    private final E[] values;
    private final ValueHolder<E> valueHolder;

    public EnumOption(@Nonnull String translationKey, @Nonnull Class<E> enumClass,
        @Nonnull ValueHolder<E> holder) {
        super(translationKey);
        this.enumClass = enumClass;
        this.values = enumClass.getEnumConstants();
        this.valueHolder = holder;
    }

    public E getValue() {
        return valueHolder.get();
    }

    public int getValueIndex() {
        E value = getValue();

        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                return i;
            }
        }

        throw new IndexOutOfBoundsException();
    }

    public ITextComponent getText() {
        return getText(valueHolder.get());
    }

    public ITextComponent getText(E value) {
        return func_243222_a(new TranslationTextComponent(value.toString()));
    }

    @Nonnull
    @Override
    public Widget createWidget(@Nonnull GameSettings options, int x, int y, int width) {
        return new OptionButton(x, y, width, BUTTON_HEIGHT, this, getText(), (p_216721_2_) -> {
            int index = (getValueIndex() + 1) % enumClass.getEnumConstants().length;
            E value = enumClass.getEnumConstants()[index];

            valueHolder.set(value);
            p_216721_2_.func_238482_a_(getText(value));
        });
    }

}
