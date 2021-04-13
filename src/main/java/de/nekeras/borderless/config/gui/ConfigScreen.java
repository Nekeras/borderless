package de.nekeras.borderless.config.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

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
        super.init();

        Minecraft minecraft = Minecraft.getInstance();
        int x = getHorizontalLayoutStart(width);

        addWidget(
                ConfigScreenOption.FULLSCREEN_MODE.createButton(minecraft.options, x, LINE_HEIGHT * 2, LAYOUT_MAX_WIDTH));

        focusLossButton = addWidget(
                ConfigScreenOption.FOCUS_LOSS.createButton(minecraft.options, x, LINE_HEIGHT * 3, LAYOUT_MAX_WIDTH));

        addWidget(new Button((width - 100) / 2, height - 75, 100, 20,
                new TranslationTextComponent("gui.done"), b -> onClose()));
    }

    @Override
    public void tick() {
        super.tick();

        focusLossButton.visible =
                ConfigScreenOption.FULLSCREEN_MODE.getValue() == FullscreenModeConfig.NATIVE;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        Minecraft minecraft = Minecraft.getInstance();

        this.renderBackground(matrixStack);

        renderTitle(matrixStack, minecraft, width);
        renderDescription(minecraft, width);
        renderChangedWarning(minecraft, width, height);

        super.render(matrixStack, mouseX, mouseY, frameTime);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(parent);
    }

    private void renderTitle(@Nonnull MatrixStack matrixStack, @Nonnull Minecraft minecraft, int width) {
        drawCenteredString(matrixStack, minecraft.font, title, width / 2, 20, 0xffffff);
    }

    private void renderDescription(@Nonnull Minecraft minecraft, int width) {
        int x = getHorizontalLayoutStart(width);
        int y = LINE_HEIGHT * 4;

        minecraft.font.drawWordWrap(new TranslationTextComponent(getDescriptionKey()), x, y, LAYOUT_MAX_WIDTH, WHITE);
    }

    private void renderChangedWarning(@Nonnull Minecraft minecraft, int width, int height) {
        int x = getHorizontalLayoutStart(width);
        int y = height - 50;

        minecraft.font.drawWordWrap(new TranslationTextComponent(CHANGED_WARNING_KEY), x, y, LAYOUT_MAX_WIDTH, YELLOW);
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

    private int getHorizontalLayoutStart(int width) {
        return (width - LAYOUT_MAX_WIDTH) / 2;
    }

}
