package de.nekeras.borderless.config.value;

import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * A {@link ValueHolder} that will set and retrieve values from
 * {@link ForgeConfigSpec.ConfigValue}.
 *
 * @param <T> The type of value an instance holds
 */
public class ConfigValueHolder<T> implements ValueHolder<T> {

    private final ForgeConfigSpec.ConfigValue<T> configValue;
    private final Consumer<T> onValueChange;

    /**
     * @param configValue The configuration value store provided by Forge
     * @param onValueChange A callback that is executed every time the provided value changes
     */
    public ConfigValueHolder(@Nonnull ForgeConfigSpec.ConfigValue<T> configValue,
        @Nullable Consumer<T> onValueChange) {
        this.configValue = configValue;
        this.onValueChange = onValueChange;
    }

    @Override
    public void set(T t) {
        configValue.set(t);
        configValue.save();

        if (onValueChange != null) {
            onValueChange.accept(t);
        }
    }

    @Override
    public T get() {
        return configValue.get();
    }

}
