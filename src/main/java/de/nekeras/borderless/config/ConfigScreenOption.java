package de.nekeras.borderless.config;

import de.nekeras.borderless.Borderless;
import de.nekeras.borderless.fullscreen.FullscreenMode;

public class ConfigScreenOption {

    public static EnumOption<FullscreenModeConfig> FULLSCREEN_MODE = new EnumOption<>(
        "borderless.config.fullscreen_mode",
        FullscreenModeConfig.class,
        ValueHolder.fromConfigValue(Config.GENERAL.fullscreenMode,
            value -> Borderless.setFullscreenMode(FullscreenMode.fromConfig()))
    );

    public static EnumOption<FocusLossConfig> FOCUS_LOSS = new EnumOption<>(
        "borderless.config.focus_loss",
        FocusLossConfig.class,
        ValueHolder.fromConfigValue(Config.GENERAL.focusLoss,
            value -> Borderless.setFullscreenMode(FullscreenMode.fromConfig()))
    );

}
