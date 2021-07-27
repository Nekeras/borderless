package de.nekeras.borderless.client.gui;

import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import de.nekeras.borderless.util.Translatable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.BooleanOption;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class ConfigScreenOption {

    private static final String ENABLED_KEY = "borderless.config.enabled";
    private static final String ENABLED_TOOLTIP_KEY = "borderless.config.enabled.tooltip";
    private static final String FULLSCREEN_MODE_KEY = "borderless.config.fullscreen_mode";
    private static final String FOCUS_LOSS_KEY = "borderless.config.focus_loss";

    public static final BooleanOption enabled = booleanOption(ENABLED_KEY, Config.GENERAL.enabled, ENABLED_TOOLTIP_KEY);

    public static final EnumOption<FullscreenModeConfig> fullscreenMode =
            enumOption(FULLSCREEN_MODE_KEY, FullscreenModeConfig.values(), Config.GENERAL.fullscreenMode);

    public static final EnumOption<FocusLossConfig> focusLoss =
            enumOption(FOCUS_LOSS_KEY, FocusLossConfig.values(), Config.GENERAL.focusLoss);

    private static BooleanOption booleanOption(String key, ForgeConfigSpec.BooleanValue config, String tooltip) {
        Predicate<GameSettings> getter = gameSettings -> config.get();
        BiConsumer<GameSettings, Boolean> setter = (gameSettings, value) -> config.set(value);

        return new BooleanOption(key, new TranslationTextComponent(tooltip), getter, setter);
    }

    private static <E extends Enum<E> & Translatable> EnumOption<E> enumOption(String key, E[] enumConstants, ForgeConfigSpec.EnumValue<E> config) {
        Function<GameSettings, E> getter = gameSettings -> config.get();
        BiConsumer<GameSettings, E> setter = (gameSettings, value) -> config.set(value);

        return new EnumOption<>(key, enumConstants, getter, setter);
    }
}
