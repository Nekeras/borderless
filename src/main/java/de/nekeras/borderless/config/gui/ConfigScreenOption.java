package de.nekeras.borderless.config.gui;

import de.nekeras.borderless.Borderless;
import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import de.nekeras.borderless.config.value.ConfigValueHolder;
import de.nekeras.borderless.fullscreen.FullscreenMode;

public class ConfigScreenOption {

    public static final EnumOption<FullscreenModeConfig> FULLSCREEN_MODE = new EnumOption<>(
        "borderless.config.fullscreen_mode",
        FullscreenModeConfig.class,
        new ConfigValueHolder<>(Config.GENERAL.fullscreenMode,
            value -> Borderless.setFullscreenMode(FullscreenMode.fromConfig()))
    );

    public static final EnumOption<FocusLossConfig> FOCUS_LOSS = new EnumOption<>(
        "borderless.config.focus_loss",
        FocusLossConfig.class,
        new ConfigValueHolder<>(Config.GENERAL.focusLoss,
            value -> Borderless.setFullscreenMode(FullscreenMode.fromConfig()))
    );

}
