package de.nekeras.borderless.config.gui;

import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
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
    private static final String CHANGED_WARNING_KEY = "borderless.config.changed";
    private static final String DESCRIPTION_KEY_BASE = "borderless.%s";

    private static final int LAYOUT_MAX_WIDTH = 250;
    private static final int WHITE = 0xffffff;
    private static final int YELLOW = 0xffff00;
    private static final int LINE_HEIGHT = 25;

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

        int x = getHorizontalLayoutStart();

        addButton(
            ConfigScreenOption.FULLSCREEN_MODE.createWidget(minecraft.gameSettings,
                x, LINE_HEIGHT * 2, LAYOUT_MAX_WIDTH));

        focusLossButton =
            addButton(ConfigScreenOption.FOCUS_LOSS.createWidget(minecraft.gameSettings,
                x, LINE_HEIGHT * 3, LAYOUT_MAX_WIDTH));

        addButton(new Button((width - 100) / 2, height - 75, 100, 20,
            I18n.format("gui.done"), b -> onClose()));
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
        renderBackground();
        renderTitle();
        renderDescription();
        renderChangedWarning();

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public void onClose() {
        assert minecraft != null;
        super.onClose();

        minecraft.displayGuiScreen(parent);
    }

    private void renderTitle() {
        assert minecraft != null;

        int x = width / 2;
        int y = LINE_HEIGHT;

        drawCenteredString(minecraft.fontRenderer, title.getFormattedText(), x, 20, WHITE);
    }

    private void renderDescription() {
        assert minecraft != null;

        int x = getHorizontalLayoutStart();
        int y = LINE_HEIGHT * 4;

        minecraft.fontRenderer
            .drawSplitString(I18n.format(getDescriptionKey()), x, y, LAYOUT_MAX_WIDTH, WHITE);
    }

    private void renderChangedWarning() {
        assert minecraft != null;

        int x = getHorizontalLayoutStart();
        int y = height - 50;

        minecraft.fontRenderer
            .drawSplitString(I18n.format(CHANGED_WARNING_KEY), x, y, LAYOUT_MAX_WIDTH, YELLOW);
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

    private int getHorizontalLayoutStart() {
        return (width - LAYOUT_MAX_WIDTH) / 2;
    }

    private int getVerticalCenter() {
        return height / 2;
    }

}
