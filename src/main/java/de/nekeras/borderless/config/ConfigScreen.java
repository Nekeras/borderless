package de.nekeras.borderless.config;

import javax.annotation.Nonnull;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfigScreen extends Screen {

    private static final String TITLE_KEY = "config.title";

    private final Screen parent;

    public ConfigScreen(@Nonnull Screen parent) {
        super(new TranslationTextComponent(TITLE_KEY));
        this.parent = parent;
    }

    @Override
    protected void init() {
        assert minecraft != null;

        addButton(
            ConfigScreenOption.FULLSCREEN_MODE
                .createWidget(minecraft.gameSettings, (width - 250) / 2, 100,
                    250));

        addButton(new Button((width - 100) / 2, height - 50, 100, 20, I18n.format("gui.done"),
            b -> onClose()));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        assert minecraft != null;
        renderBackground();

        FullscreenModeConfig config = ConfigScreenOption.FULLSCREEN_MODE.getValue();

        minecraft.fontRenderer
            .drawSplitString(I18n.format(config.getDescriptionKey()), (width - 250) / 2, 130,
                250, 0xffffff);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public void onClose() {
        assert minecraft != null;

        minecraft.displayGuiScreen(parent);
    }
}
