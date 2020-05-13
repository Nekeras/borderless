package de.nekeras.borderless.config;

import de.nekeras.borderless.Borderless;

public class ConfigScreenOption {

    public static EnumOption<FullscreenModeConfig> FULLSCREEN_MODE = new EnumOption<>(
        "borderless.config.fullscreen_mode",
        FullscreenModeConfig.class,
        ValueHolder.fromConfigValue(Config.GENERAL.fullscreenMode,
            value -> Borderless.setFullscreenMode(value.newFullscreenMode()))
    );

}
