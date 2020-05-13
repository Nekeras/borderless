package de.nekeras.borderless.config;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeConfigSpec;

public interface ValueHolder<T> {

    void set(T t);

    T get();

    static <T> ValueHolder<T> fromConfigValue(@Nonnull ForgeConfigSpec.ConfigValue<T> configValue,
        @Nullable Consumer<T> onValueChange) {
        return new ValueHolder<T>() {
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
        };
    }

    static <T> ValueHolder<T> fromCallback(@Nonnull Consumer<T> setter,
        @Nonnull Supplier<T> getter) {
        return new ValueHolder<T>() {
            @Override
            public void set(T t) {
                setter.accept(t);
            }

            @Override
            public T get() {
                return getter.get();
            }
        };
    }

}
