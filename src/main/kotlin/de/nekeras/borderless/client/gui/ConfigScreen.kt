package de.nekeras.borderless.client.gui

import com.mojang.blaze3d.matrix.MatrixStack
import de.nekeras.borderless.config.FocusLossConfig
import de.nekeras.borderless.config.FullscreenModeConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.client.gui.widget.button.OptionButton
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class ConfigScreen(private val parent: Screen) : Screen(title) {

    private lateinit var fullscreenModeButton: OptionButton
    private lateinit var focusLossButton: OptionButton

    override fun init() = withMinecraft {
        super.init()

        val x = getHorizontalLayoutStart(width)
        fullscreenModeButton = ConfigScreenOption.fullscreen.createButton(options, x, LINE_HEIGHT * 2, LAYOUT_MAX_WIDTH)
        focusLossButton = ConfigScreenOption.focusLoss.createButton(options, x, LINE_HEIGHT * 3, LAYOUT_MAX_WIDTH)

        val done = Button((width - 100) / 2, height - 75, 100, 20, TranslationTextComponent("gui.done")) {
            onClose()
        }

        addButton(fullscreenModeButton)
        addButton(focusLossButton)
        addButton(done)
    }

    override fun tick() {
        super.tick()
        focusLossButton.visible = ConfigScreenOption.fullscreen.value == FullscreenModeConfig.NATIVE
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
        val y: Int = LINE_HEIGHT * 4

        font.drawWordWrap(TranslationTextComponent(getDescriptionKey()), x, y, LAYOUT_MAX_WIDTH, WHITE)
    }

    private fun renderChangedWarning(width: Int, height: Int) = withMinecraft {
        val x = getHorizontalLayoutStart(width)
        val y = height - 50

        font.drawWordWrap(changedWarning, x, y, LAYOUT_MAX_WIDTH, YELLOW)
    }

    private fun getDescriptionKey(): String {
        val mode: FullscreenModeConfig = ConfigScreenOption.fullscreen.value

        return if (mode != FullscreenModeConfig.NATIVE) {
            "$DESCRIPTION_KEY_BASE.${mode.name.toLowerCase()}"
        } else {
            val focusLoss: FocusLossConfig = ConfigScreenOption.focusLoss.value
            "$DESCRIPTION_KEY_BASE.${mode.name.toLowerCase()}.${focusLoss.name.toLowerCase()}"
        }
    }

    private fun getHorizontalLayoutStart(width: Int): Int = (width - LAYOUT_MAX_WIDTH) / 2

    private inline fun withMinecraft(action: Minecraft.() -> Unit) = with(minecraft ?: Minecraft.getInstance(), action)

    companion object {

        private const val DESCRIPTION_KEY_BASE = "borderless"
        private const val LAYOUT_MAX_WIDTH = 250
        private const val WHITE = 0xffffff
        private const val YELLOW = 0xffff00
        private const val LINE_HEIGHT = 25

        private val title = TranslationTextComponent("borderless.config.title")
        private val changedWarning = TranslationTextComponent("borderless.config.changed")
    }
}
