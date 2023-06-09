package de.nekeras.borderless.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.nekeras.borderless.client.FullscreenDisplayModeHolder;
import de.nekeras.borderless.config.Config;
import de.nekeras.borderless.config.FocusLossConfig;
import de.nekeras.borderless.config.FullscreenModeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
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
    private static final Component titleText = Component.translatable("borderless.config.title");
    private static final Component applyText = Component.translatable("borderless.config.apply");
    private static final Component changedWarningText = Component.translatable("borderless.config.changed");
    private static final Component disabledText = Component.translatable("borderless.config.disabled.description");
    private static final Logger log = LogManager.getLogger();

    private final Screen parent;

    private AbstractWidget enabledButton;
    private AbstractWidget fullscreenModeButton;
    private AbstractWidget focusLossButton;

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

        Button applyButton = Button.builder(applyText, btn -> {
            log.info("Apply button in Borderless Window Config Screen pressed");
            FullscreenDisplayModeHolder.setFullscreenDisplayModeFromConfig();
            onClose();
        }).bounds(width / 2 - 125, height - 75, 100, 20).build();

        Button cancelButton = Button.builder(CommonComponents.GUI_CANCEL, btn -> {
            log.info("Cancel button in Borderless Window Config Screen pressed, resetting to {}, {}, {}",
                    initialEnabledState, initialFullscreenMode, initialFocusLossMode);
            Config.GENERAL.enabled.set(initialEnabledState);
            Config.GENERAL.fullscreenMode.set(initialFullscreenMode);
            Config.GENERAL.focusLoss.set(initialFocusLossMode);
            onClose();
        }).bounds(width / 2 + 25, height - 75, 100, 20).build();

        addRenderableWidget(enabledButton);
        addRenderableWidget(fullscreenModeButton);
        addRenderableWidget(focusLossButton);
        addRenderableWidget(applyButton);
        addRenderableWidget(cancelButton);

        refreshButtonStates();
    }

    @Override
    public void tick() {
        super.tick();

        refreshButtonStates();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float frameTime) {
        Minecraft minecraft = Minecraft.getInstance();

        this.renderBackground(guiGraphics);

        renderTitle(guiGraphics, minecraft, width);
        renderDescription(guiGraphics, minecraft, width);
        renderChangedWarning(guiGraphics, minecraft, width, height);

        super.render(guiGraphics, mouseX, mouseY, frameTime);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(parent);
    }

    private void renderTitle(@Nonnull GuiGraphics guiGraphics, @Nonnull Minecraft minecraft, int width) {
        guiGraphics.drawCenteredString(minecraft.font, title, width / 2, 20, WHITE);
    }

    private void renderDescription(@Nonnull GuiGraphics guiGraphics, @Nonnull Minecraft minecraft, int width) {
        int x = getHorizontalLayoutStart(width);
        int y = LINE_HEIGHT * 5;

        if (Config.GENERAL.enabled.get()) {
            guiGraphics.drawWordWrap(minecraft.font, Component.translatable(getDescriptionKey()), x, y, LAYOUT_MAX_WIDTH, WHITE);
        } else {
            guiGraphics.drawWordWrap(minecraft.font, disabledText, x, y, LAYOUT_MAX_WIDTH, RED);
        }
    }

    private void renderChangedWarning(@Nonnull GuiGraphics guiGraphics, @Nonnull Minecraft minecraft, int width, int height) {
        int x = getHorizontalLayoutStart(width);
        int y = height - 50;

        guiGraphics.drawWordWrap(minecraft.font, changedWarningText, x, y, LAYOUT_MAX_WIDTH, YELLOW);
    }

    private String getDescriptionKey() {
        FullscreenModeConfig mode = Config.GENERAL.fullscreenMode.get();
        String modeKey = String.format(DESCRIPTION_KEY_BASE, mode.name().toLowerCase());

        if (mode == FullscreenModeConfig.NATIVE) {
            FocusLossConfig focusLoss = Config.GENERAL.focusLoss.get();

            return String.format("%s.%s", modeKey, focusLoss.name().toLowerCase());
        } else {
            return modeKey;
        }
    }

    private void refreshButtonStates() {
        boolean enabled = Config.GENERAL.enabled.get();

        enabledButton.visible = true;
        fullscreenModeButton.visible = enabled;
        focusLossButton.visible = enabled && Config.GENERAL.fullscreenMode.get() == FullscreenModeConfig.NATIVE;
    }

    private int getHorizontalLayoutStart(int width) {
        return (width - LAYOUT_MAX_WIDTH) / 2;
    }

}
