package de.nekeras.borderless.client.gui

import de.nekeras.borderless.Translatable
import de.nekeras.borderless.client.FullscreenModeHolder
import de.nekeras.borderless.config.Config
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.ForgeConfigSpec

@OnlyIn(Dist.CLIENT)
object ConfigScreenOption {

    private const val FULLSCREEN_MODE_KEY = "borderless.config.fullscreen_mode"
    private const val FOCUS_LOSS_KEY = "borderless.config.focus_loss"

    val fullscreen = enumOption(FULLSCREEN_MODE_KEY, Config.fullscreenMode) { _, _ ->
        FullscreenModeHolder.refreshFullscreenModeFromConfig()
    }

    val focusLoss = enumOption(FOCUS_LOSS_KEY, Config.focusLoss) { _, _ ->
        FullscreenModeHolder.refreshFullscreenModeFromConfig()
    }

    private inline fun <reified T> enumOption(
        translationKey: String,
        config: ForgeConfigSpec.EnumValue<T>,
        noinline onChange: (T, T) -> Unit
    ): EnumOption<T> where T : Translatable, T : Enum<T> {
        val enumConstants: Array<T> = T::class.java.enumConstants
        return EnumOption(translationKey, config, enumConstants, onChange)
    }
}
