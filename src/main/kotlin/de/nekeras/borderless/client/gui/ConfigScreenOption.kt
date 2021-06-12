package de.nekeras.borderless.client.gui

import de.nekeras.borderless.Translatable
import de.nekeras.borderless.config.Config
import net.minecraft.client.settings.BooleanOption
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.ForgeConfigSpec

@OnlyIn(Dist.CLIENT)
object ConfigScreenOption {

    private const val ENABLED_KEY = "borderless.config.enabled"
    private const val ENABLED_TOOLTIP_KEY = "borderless.config.enabled.tooltip"
    private const val FULLSCREEN_MODE_KEY = "borderless.config.fullscreen_mode"
    private const val FOCUS_LOSS_KEY = "borderless.config.focus_loss"

    val enabled = booleanOption(ENABLED_KEY, Config.enabledConfig, ENABLED_TOOLTIP_KEY)

    val fullscreen = enumOption(FULLSCREEN_MODE_KEY, Config.fullscreenModeConfig)

    val focusLoss = enumOption(FOCUS_LOSS_KEY, Config.focusLossConfig)

    private fun booleanOption(
        translationKey: String,
        config: ForgeConfigSpec.BooleanValue,
        tooltipKey: String? = null
    ): BooleanOption =
        BooleanOption(translationKey, tooltipKey?.let { TranslationTextComponent(it) }, {
            config.get()
        }, { _, newValue ->
            config.set(newValue)
        })

    private inline fun <reified T> enumOption(
        translationKey: String,
        config: ForgeConfigSpec.EnumValue<T>
    ): EnumOption<T> where T : Translatable, T : Enum<T> {
        val enumConstants: Array<T> = T::class.java.enumConstants
        return EnumOption(translationKey, enumConstants, {
            config.get()
        }, { _, newValue ->
            config.set(newValue)
        })
    }
}
