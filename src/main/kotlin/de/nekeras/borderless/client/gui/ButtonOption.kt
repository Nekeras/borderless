package de.nekeras.borderless.client.gui

import net.minecraft.client.AbstractOption
import net.minecraft.client.GameSettings
import net.minecraft.client.gui.widget.Widget
import net.minecraft.client.gui.widget.button.OptionButton

class ButtonOption(title: String, private val onButtonClick: (Widget) -> Unit) : AbstractOption(title) {

    override fun createButton(options: GameSettings, x: Int, y: Int, width: Int): Widget =
        OptionButton(x, y, width, BUTTON_HEIGHT, this, caption, onButtonClick)
    
    companion object {

        private const val BUTTON_HEIGHT = 20
    }
}
