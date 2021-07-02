package de.nekeras.borderless.client.listener

import de.nekeras.borderless.client.gui.ButtonOption
import de.nekeras.borderless.client.gui.ConfigScreen
import de.nekeras.borderless.tryMakeFieldAccessible
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.VideoSettingsScreen
import net.minecraft.client.gui.widget.list.OptionsRowList
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.client.event.GuiScreenEvent

object VideoSettingsListener {

    private const val TITLE_KEY = "borderless.config.video_settings_button"

    private val tooltip = TranslationTextComponent("${TITLE_KEY}.tooltip")
    private val VideoSettingsScreen.optionsRowList by tryMakeFieldAccessible<VideoSettingsScreen, OptionsRowList>()

    fun onVideoSettings(event: GuiScreenEvent.InitGuiEvent.Post) {
        val gui = event.gui

        if (gui is VideoSettingsScreen) {
            gui.optionsRowList?.let { rowList ->
                val fullscreenOption = ButtonOption(TITLE_KEY) { Minecraft.getInstance().setScreen(ConfigScreen(gui)) }
                fullscreenOption.setTooltip(Minecraft.getInstance().font.split(tooltip, 200))
                rowList.addBig(fullscreenOption)
                val last = rowList.children().removeLast()
                rowList.children().add(0, last)
            }
        }
    }
}
