package de.nekeras.borderless.config;

import javax.annotation.Nonnull;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AbstractOption;

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

    public Class<E> getEnumClass() {
        return enumClass;
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

    public void setValue(@Nonnull E value) {
        valueHolder.set(value);
    }

    public void setValueIndex(int index) {
        setValue(enumClass.getEnumConstants()[index]);
    }

    public String getText() {
        return getDisplayString() + I18n.format(getValue().toString());
    }

    @Nonnull
    @Override
    public Widget createWidget(@Nonnull GameSettings options, int x, int y, int width) {
        return new OptionButton(x, y, width, BUTTON_HEIGHT, this, getText(), (p_216721_2_) -> {
            setValueIndex((getValueIndex() + 1) % values.length);
            p_216721_2_.setMessage(getText());
        });
    }

}
