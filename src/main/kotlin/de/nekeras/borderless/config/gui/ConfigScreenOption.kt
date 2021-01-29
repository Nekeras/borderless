package de.nekeras.borderless.config.gui

import de.nekeras.borderless.config.FocusLossConfig
import de.nekeras.borderless.config.FullscreenModeConfig

object ConfigScreenOption {

    private const val FULLSCREEN_MODE_KEY = "borderless.config.fullscreen_mode"
    private const val FOCUS_LOSS_KEY = "borderless.config.focus_loss"

    val fullscreenModeOption =
        ArrayOption(FULLSCREEN_MODE_KEY, FullscreenModeConfig.values()) { oldValue, newValue ->
        }

    val focusLossOption =
        ArrayOption(FOCUS_LOSS_KEY, FocusLossConfig.values()) { oldValue, newValue ->

        }

}
