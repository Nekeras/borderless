package de.nekeras.borderless.client.gui

import de.nekeras.borderless.Translatable
import net.minecraft.client.AbstractOption
import net.minecraft.client.GameSettings
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.client.gui.widget.button.OptionButton
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.ForgeConfigSpec

@OnlyIn(Dist.CLIENT)
class EnumOption<T>(
    translationKey: String,
    private val config: ForgeConfigSpec.EnumValue<T>,
    private val enumConstants: Array<T>,
    private val onChange: (T, T) -> Unit
) : AbstractOption(translationKey) where T : Translatable, T : Enum<T> {

    val value: T
        get() = config.get()

    val title: ITextComponent
        get() = value.translation

    override fun createButton(options: GameSettings, x: Int, y: Int, width: Int) =
        OptionButton(x, y, width, BUTTON_HEIGHT, this, title, this::onButtonClick)

    private fun onButtonClick(button: Button) {
        val oldValue = value

        val index = (value.ordinal + 1) % enumConstants.size
        config.set(enumConstants.getOrNull(index))
        button.message = title

        onChange(oldValue, value)
    }

    companion object {

        private const val BUTTON_HEIGHT = 20
    }
}
