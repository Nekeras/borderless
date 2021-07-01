package de.nekeras.borderless.client.gui

import com.mojang.blaze3d.matrix.MatrixStack
import de.nekeras.borderless.client.FullscreenModeHolder
import de.nekeras.borderless.config.Config
import de.nekeras.borderless.config.FocusLossConfig
import de.nekeras.borderless.config.FullscreenModeConfig
import net.minecraft.client.GameSettings
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.DialogTexts
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.Widget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.Locale

@OnlyIn(Dist.CLIENT)
class ConfigScreen(private val parent: Screen) : Screen(titleText) {

    private var initialEnabledState: Boolean = false
    private lateinit var initialFullscreenMode: FullscreenModeConfig
    private lateinit var initialFocusLossMode: FocusLossConfig

    private lateinit var enabledButton: Widget
    private lateinit var fullscreenModeButton: Widget
    private lateinit var focusLossButton: Widget

    override fun init() = withMinecraft {
        super.init()

        initialEnabledState = Config.enabledConfig.get()
        initialFullscreenMode = Config.fullscreenModeConfig.get()
        initialFocusLossMode = Config.focusLossConfig.get()

        val x = getHorizontalLayoutStart(width)
        enabledButton = ConfigScreenOption.enabled.createButton(options, x, LINE_HEIGHT * 2, LAYOUT_MAX_WIDTH)
        fullscreenModeButton = ConfigScreenOption.fullscreen.createButton(options, x, LINE_HEIGHT * 3, LAYOUT_MAX_WIDTH)
        focusLossButton = ConfigScreenOption.focusLoss.createButton(options, x, LINE_HEIGHT * 4, LAYOUT_MAX_WIDTH)

        val apply = Button(width / 2 - 125, height - 75, 100, 20, applyText) {
            FullscreenModeHolder.refreshFullscreenModeFromConfig()
            onClose()
        }

        val cancel = Button(width / 2 + 25, height - 75, 100, 20, DialogTexts.GUI_CANCEL) {
            Config.enabledConfig.set(initialEnabledState)
            Config.fullscreenModeConfig.set(initialFullscreenMode)
            Config.focusLossConfig.set(initialFocusLossMode)
            onClose()
        }

        addButton(enabledButton)
        addButton(fullscreenModeButton)
        addButton(focusLossButton)
        addButton(apply)
        addButton(cancel)

        refreshButtonStates()
    }

    override fun tick() {
        super.tick()
        refreshButtonStates()
    }

    override fun render(matrixStack: MatrixStack, x: Int, y: Int, frameTime: Float) {
        super.renderBackground(matrixStack)
        renderTitle(matrixStack, width)
        renderDescription(width)
        renderChangedWarning(width, height)
        super.render(matrixStack, x, y, frameTime)
    }

    override fun onClose() {
        super.onClose()
        Minecraft.getInstance().screen = parent
    }

    private fun renderTitle(matrixStack: MatrixStack, width: Int) = withMinecraft {
        AbstractGui.drawCenteredString(matrixStack, font, title, width / 2, 20, WHITE)
    }

    private fun renderDescription(width: Int) = withMinecraft {
        val x = getHorizontalLayoutStart(width)
        val y: Int = LINE_HEIGHT * 5

        if (ConfigScreenOption.enabled.get(options)) {
            font.drawWordWrap(TranslationTextComponent(getDescriptionKey(options)), x, y, LAYOUT_MAX_WIDTH, WHITE)
        } else {
            font.drawWordWrap(disabledDescription, x, y, LAYOUT_MAX_WIDTH, RED)
        }
    }

    private fun renderChangedWarning(width: Int, height: Int) = withMinecraft {
        val x = getHorizontalLayoutStart(width)
        val y = height - 50

        font.drawWordWrap(changedWarningText, x, y, LAYOUT_MAX_WIDTH, YELLOW)
    }

    private fun refreshButtonStates() = withMinecraft {
        val enabled = ConfigScreenOption.enabled.get(options)

        fullscreenModeButton.visible = enabled
        focusLossButton.visible = enabled && ConfigScreenOption.fullscreen.get(options) == FullscreenModeConfig.NATIVE
    }

    private fun getDescriptionKey(settings: GameSettings): String {
        val mode: FullscreenModeConfig = ConfigScreenOption.fullscreen.get(settings)
        val modeName = mode.name.lowercase(Locale.getDefault())

        return if (mode != FullscreenModeConfig.NATIVE) {
            "$DESCRIPTION_KEY_BASE.$modeName"
        } else {
            val focusLoss: FocusLossConfig = ConfigScreenOption.focusLoss.get(settings)
            val focusLossName = focusLoss.name.lowercase(Locale.getDefault())
            "$DESCRIPTION_KEY_BASE.$modeName.$focusLossName"
        }
    }

    private fun getHorizontalLayoutStart(width: Int): Int = (width - LAYOUT_MAX_WIDTH) / 2

    private inline fun withMinecraft(action: Minecraft.() -> Unit) = with(minecraft ?: Minecraft.getInstance(), action)

    companion object {

        private const val DESCRIPTION_KEY_BASE = "borderless"
        private const val LAYOUT_MAX_WIDTH = 250
        private const val WHITE = 0xffffff
        private const val YELLOW = 0xffff00
        private const val RED = 0xff0000
        private const val LINE_HEIGHT = 25

        private val titleText = TranslationTextComponent("borderless.config.title")
        private val applyText = TranslationTextComponent("borderless.config.apply")
        private val changedWarningText = TranslationTextComponent("borderless.config.changed")
        private val disabledDescription = TranslationTextComponent("borderless.config.disabled.description")
    }
}
