package de.nekeras.borderless.config.gui

import de.nekeras.borderless.Translatable
import net.minecraft.client.AbstractOption
import net.minecraft.client.GameSettings
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.client.gui.widget.button.OptionButton
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class ArrayOption<T : Translatable>(
    translationKey: String,
    private val values: Array<T>,
    private val onChange: (T, T) -> Unit
) : AbstractOption(translationKey) {

    var valueIndex: Int = 0

    val value: T?
        get() = values.getOrNull(valueIndex)

    val title
        get() = value?.let { TranslationTextComponent(it.translationKey) }
            ?: StringTextComponent("")

    override fun createWidget(options: GameSettings, xIn: Int, yIn: Int, widthIn: Int) =
        OptionButton(xIn, yIn, widthIn, BUTTON_HEIGHT, this, title, this::onButtonClick)

    private fun onButtonClick(button: Button) {
        if (values.isNotEmpty()) {
            val oldValue = values[valueIndex]

            valueIndex = (valueIndex + 1) % values.size
            button.message = title

            val newValue = values[valueIndex]
            onChange(oldValue, newValue)
        }
    }

    companion object {

        private const val BUTTON_HEIGHT = 20
    }
}
