package de.nekeras.borderless.config.gui;

import javax.annotation.Nonnull;

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
    protected void func_231160_c_() {
        // init
        super.func_231160_c_();

        Minecraft minecraft = Minecraft.getInstance();
        int width = field_230708_k_;
        int height = field_230709_l_;
        int x = getHorizontalLayoutStart(width);

        func_230480_a_(
            ConfigScreenOption.FULLSCREEN_MODE.createWidget(minecraft.gameSettings,
                x, LINE_HEIGHT * 2, LAYOUT_MAX_WIDTH));

        focusLossButton =
            func_230480_a_(ConfigScreenOption.FOCUS_LOSS.createWidget(minecraft.gameSettings,
                x, LINE_HEIGHT * 3, LAYOUT_MAX_WIDTH));

        func_230480_a_(new Button((width - 100) / 2, height - 75, 100, 20,
            new TranslationTextComponent("gui.done"), b -> func_231175_as__()));
    }

    @Override
    public void func_231023_e_() {
        // Tick
        super.func_231023_e_();

        // visible
        focusLossButton.field_230694_p_ =
            ConfigScreenOption.FULLSCREEN_MODE.getValue() == FullscreenModeConfig.NATIVE;
    }

    @Override
    public void func_230430_a_(@Nonnull MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_,
        float p_230430_4_) {
        // render
        Minecraft minecraft = Minecraft.getInstance();
        int width = field_230708_k_;
        int height = field_230709_l_;

        // renderBackground
        this.func_230446_a_(p_230430_1_);

        renderTitle(p_230430_1_, minecraft, width, height);
        renderDescription(p_230430_1_, minecraft, width, height);
        renderChangedWarning(p_230430_1_, minecraft, width, height);

        super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    @Override
    public void func_231175_as__() {
        // onClose
        super.func_231175_as__();
        Minecraft.getInstance().displayGuiScreen(parent);
    }

    private void renderTitle(MatrixStack matrixStack, Minecraft minecraft, int width, int height) {
        int x = width / 2;
        int y = LINE_HEIGHT;

        // drawCenteredString
        func_238472_a_(matrixStack, minecraft.fontRenderer, field_230704_d_, width / 2,
            20,
            0xffffff);
    }

    private void renderDescription(MatrixStack matrixStack, Minecraft minecraft, int width,
        int height) {
        int x = getHorizontalLayoutStart(width);
        int y = LINE_HEIGHT * 4;

        // drawSplitString
        minecraft.fontRenderer
            .func_238418_a_(new TranslationTextComponent(getDescriptionKey()), x, y,
                LAYOUT_MAX_WIDTH, WHITE);
    }

    private void renderChangedWarning(MatrixStack matrixStack, Minecraft minecraft, int width,
        int height) {
        int x = getHorizontalLayoutStart(width);
        int y = height - 50;

        // drawSplitString
        minecraft.fontRenderer
            .func_238418_a_(new TranslationTextComponent(CHANGED_WARNING_KEY), x, y,
                LAYOUT_MAX_WIDTH, YELLOW);
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
