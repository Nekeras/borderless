package de.nekeras.borderless.config.gui

import de.nekeras.borderless.config.FullscreenModeConfig
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.TranslationTextComponent

class ConfigScreen(private val parent: Screen) : Screen(TranslationTextComponent(TITLE_KEY)) {

    private var fullscreenButton = addButton(null)

    override fun init() {
        super.init()
    }

    companion object {

        private const val TITLE_KEY = "borderless.config.title"
        private const val WHITE = 0xffffff
        private const val YELLOW = 0xffff00
        private const val LINE_HEIGHT = 25

        private val fullscreenOption = ArrayOption(
            "borderless.config.fullscreen_mode",
            FullscreenModeConfig.values()
        ) { _, newValue ->
        }

    }

}
