package de.nekeras.borderless.config;

import javax.annotation.Nonnull;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfigScreen extends Screen {

    private static final String TITLE_KEY = "borderless.config.title";
    private static final String DESCRIPTION_KEY_BASE = "borderless.%s";

    private final Screen parent;

    private Widget focusLossButton;

    public ConfigScreen(@Nonnull Screen parent) {
        super(new TranslationTextComponent(TITLE_KEY));
        this.parent = parent;
    }

    @Override
    protected void init() {
        assert minecraft != null;
        super.init();

        addButton(
            ConfigScreenOption.FULLSCREEN_MODE.createWidget(minecraft.gameSettings,
                (width - 250) / 2, (height - 100) / 2, 250));

        focusLossButton =
            addButton(ConfigScreenOption.FOCUS_LOSS.createWidget(minecraft.gameSettings,
                (width - 250) / 2, (height - 50) / 2, 250));

        addButton(new Button((width - 100) / 2, height - 50, 100, 20, I18n.format("gui.done"),
            b -> onClose()));
    }

    @Override
    public void tick() {
        assert minecraft != null;
        super.tick();

        focusLossButton.visible =
            ConfigScreenOption.FULLSCREEN_MODE.getValue() == FullscreenModeConfig.NATIVE;
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        assert minecraft != null;
        renderBackground();

        drawCenteredString(minecraft.fontRenderer, title.getFormattedText(), width / 2, 20,
            0xffffff);

        minecraft.fontRenderer
            .drawSplitString(I18n.format(getDescriptionKey()), (width - 250) / 2, height / 2,
                250, 0xffffff);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public void onClose() {
        assert minecraft != null;
        super.onClose();

        minecraft.displayGuiScreen(parent);
    }

    private String getDescriptionKey() {
        FullscreenModeConfig mode = ConfigScreenOption.FULLSCREEN_MODE.getValue();
        String modeKey = String.format(DESCRIPTION_KEY_BASE, mode.name().toLowerCase());

        if (mode != FullscreenModeConfig.NATIVE) {
            return modeKey;
        } else {
            FocusLossConfig focusLoss = ConfigScreenOption.FOCUS_LOSS.getValue();

            return String.format("%s.%s", modeKey, focusLoss.name().toLowerCase());
        }
    }

}
