package de.nekeras.borderless.client.gui

import de.nekeras.borderless.Translatable
import net.minecraft.client.AbstractOption
import net.minecraft.client.GameSettings
import net.minecraft.client.gui.widget.Widget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.client.gui.widget.button.OptionButton
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class EnumOption<T>(
    translationKey: String,
    private val enumConstants: Array<T>,
    private val getter: (GameSettings) -> T,
    private val setter: (GameSettings, T) -> Unit
) : AbstractOption(translationKey) where T : Translatable, T : Enum<T> {

    fun get(settings: GameSettings): T = getter(settings)

    fun getMessage(settings: GameSettings): ITextComponent = genericValueLabel(get(settings).translation)

    override fun createButton(settings: GameSettings, x: Int, y: Int, width: Int): Widget =
        OptionButton(x, y, width, BUTTON_HEIGHT, this, getMessage(settings)) {
            onButtonClick(settings, it)
        }

    private fun onButtonClick(settings: GameSettings, button: Button) {
        val value = get(settings)
        val nextValueIndex = (value.ordinal + 1) % enumConstants.size
        val nextValue = enumConstants[nextValueIndex]

        setter(settings, nextValue)
        settings.save()

        val title = getMessage(settings)
        button.message = title
    }

    companion object {

        private const val BUTTON_HEIGHT = 20
    }
}
