package de.nekeras.borderless.client.gui;

import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import net.minecraft.client.CycleOption;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfigScreenOption {

    private static final String ENABLED_KEY = "borderless.config.enabled";
    private static final String FULLSCREEN_MODE_KEY = "borderless.config.fullscreen_mode";
    private static final String FOCUS_LOSS_KEY = "borderless.config.focus_loss";
    private static final Component enabledTooltip = new TranslatableComponent("borderless.config.enabled.tooltip");

    public static final CycleOption<Boolean> enabled = CycleOption.createOnOff(
            ENABLED_KEY,
            enabledTooltip,
            options -> Config.GENERAL.enabled.get(),
            (options, opt, value) -> Config.GENERAL.enabled.set(value));

    public static final CycleOption<FullscreenModeConfig> fullscreenMode = CycleOption.create(
            FULLSCREEN_MODE_KEY,
            FullscreenModeConfig.values(),
            FullscreenModeConfig::getTranslation,
            options -> Config.GENERAL.fullscreenMode.get(),
            (options, opt, value) -> Config.GENERAL.fullscreenMode.set(value)
    );

    public static final CycleOption<FocusLossConfig> focusLoss = CycleOption.create(
            FOCUS_LOSS_KEY,
            FocusLossConfig.values(),
            FocusLossConfig::getTranslation,
            options -> Config.GENERAL.focusLoss.get(),
            (options, opt, value) -> Config.GENERAL.focusLoss.set(value)
    );
}
