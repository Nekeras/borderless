package de.nekeras.borderless.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.nekeras.borderless.client.FullscreenDisplayModeHolder;
import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ConfigScreen extends Screen {

    private static final String DESCRIPTION_KEY_BASE = "borderless.%s";
    private static final int LAYOUT_MAX_WIDTH = 250;
    private static final int WHITE = 0xffffff;
    private static final int YELLOW = 0xffff00;
    private static final int RED = 0xff0000;
    private static final int LINE_HEIGHT = 25;
    private static final ITextComponent titleText = new TranslationTextComponent("borderless.config.title");
    private static final ITextComponent applyText = new TranslationTextComponent("borderless.config.apply");
    private static final ITextComponent changedWarningText = new TranslationTextComponent("borderless.config.changed");
    private static final ITextComponent disabledText = new TranslationTextComponent("borderless.config.disabled.description");
    private static final Logger log = LogManager.getLogger();

    private final Screen parent;

    private Widget enabledButton;
    private Widget fullscreenModeButton;
    private Widget focusLossButton;

    public ConfigScreen(@Nonnull Screen parent) {
        super(titleText);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        boolean initialEnabledState = Config.GENERAL.enabled.get();
        FullscreenModeConfig initialFullscreenMode = Config.GENERAL.fullscreenMode.get();
        FocusLossConfig initialFocusLossMode = Config.GENERAL.focusLoss.get();

        Minecraft minecraft = Minecraft.getInstance();
        int x = getHorizontalLayoutStart(width);

        enabledButton = ConfigScreenOption.enabled.createButton(minecraft.options, x, LINE_HEIGHT * 2, LAYOUT_MAX_WIDTH);
        fullscreenModeButton = ConfigScreenOption.fullscreenMode.createButton(minecraft.options, x, LINE_HEIGHT * 3, LAYOUT_MAX_WIDTH);
        focusLossButton = ConfigScreenOption.focusLoss.createButton(minecraft.options, x, LINE_HEIGHT * 4, LAYOUT_MAX_WIDTH);

        Button applyButton = new Button(width / 2 - 125, height - 75, 100, 20, applyText, btn -> {
            log.info("Apply button in Borderless Window Config Screen pressed");
            FullscreenDisplayModeHolder.setFullscreenDisplayModeFromConfig();
            onClose();
        });

        Button cancelButton = new Button(width / 2 + 25, height - 75, 100, 20, DialogTexts.GUI_CANCEL, btn -> {
            log.info("Cancel button in Borderless Window Config Screen pressed, resetting to {}, {}, {}",
                    initialEnabledState, initialFullscreenMode, initialFocusLossMode);
            Config.GENERAL.enabled.set(initialEnabledState);
            Config.GENERAL.fullscreenMode.set(initialFullscreenMode);
            Config.GENERAL.focusLoss.set(initialFocusLossMode);
            onClose();
        });

        addButton(enabledButton);
        addButton(fullscreenModeButton);
        addButton(focusLossButton);
        addButton(applyButton);
        addButton(cancelButton);

        refreshButtonStates();
    }

    @Override
    public void tick() {
        super.tick();

        refreshButtonStates();
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
        drawCenteredString(matrixStack, minecraft.font, title, width / 2, 20, WHITE);
    }

    private void renderDescription(@Nonnull Minecraft minecraft, int width) {
        int x = getHorizontalLayoutStart(width);
        int y = LINE_HEIGHT * 5;

        if (ConfigScreenOption.enabled.get(minecraft.options)) {
            minecraft.font.drawWordWrap(new TranslationTextComponent(getDescriptionKey()), x, y, LAYOUT_MAX_WIDTH, WHITE);
        } else {
            minecraft.font.drawWordWrap(disabledText, x, y, LAYOUT_MAX_WIDTH, RED);
        }
    }

    private void renderChangedWarning(@Nonnull Minecraft minecraft, int width, int height) {
        int x = getHorizontalLayoutStart(width);
        int y = height - 50;

        minecraft.font.drawWordWrap(changedWarningText, x, y, LAYOUT_MAX_WIDTH, YELLOW);
    }

    private String getDescriptionKey() {
        Minecraft minecraft = Minecraft.getInstance();

        FullscreenModeConfig mode = ConfigScreenOption.fullscreenMode.getValue(minecraft.options);
        String modeKey = String.format(DESCRIPTION_KEY_BASE, mode.name().toLowerCase());

        if (mode != FullscreenModeConfig.NATIVE) {
            return modeKey;
        } else {
            FocusLossConfig focusLoss = ConfigScreenOption.focusLoss.getValue(minecraft.options);

            return String.format("%s.%s", modeKey, focusLoss.name().toLowerCase());
        }
    }

    private void refreshButtonStates() {
        Minecraft minecraft = Minecraft.getInstance();

        boolean enabled = ConfigScreenOption.enabled.get(minecraft.options);

        fullscreenModeButton.visible = enabled;
        focusLossButton.visible = enabled &&
                ConfigScreenOption.fullscreenMode.getValue(minecraft.options) == FullscreenModeConfig.NATIVE;
    }

    private int getHorizontalLayoutStart(int width) {
        return (width - LAYOUT_MAX_WIDTH) / 2;
    }

}
