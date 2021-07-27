package de.nekeras.borderless.client.listener;

import de.nekeras.borderless.BorderlessWindow;
import de.nekeras.borderless.client.ReflectionUtils;
import de.nekeras.borderless.client.gui.ButtonOption;
import de.nekeras.borderless.client.gui.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BorderlessWindow.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VideoSettingsListener {

    private static final String TITLE_KEY = "borderless.config.video_settings_button";
    private static final ITextComponent tooltip = new TranslationTextComponent("borderless.config.video_settings_button.tooltip");
    private static final Logger log = LogManager.getLogger();

    @SubscribeEvent
    public static void onVideoSettings(@Nonnull GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof VideoSettingsScreen) {
            VideoSettingsScreen screen = (VideoSettingsScreen) event.getGui();
            log.info("Opened VideoSettingsScreen");

            ReflectionUtils.getOptionsRowList(screen).ifPresent(
                    optionsRowList -> addToOptionsRowList(screen, optionsRowList));
        }
    }

    private static void addToOptionsRowList(@Nonnull VideoSettingsScreen screen, @Nonnull OptionsRowList optionsRowList) {
        log.info("Found OptionsRowList");
        Minecraft minecraft = Minecraft.getInstance();

        ButtonOption fullscreenOption = new ButtonOption(TITLE_KEY,
                btn -> minecraft.setScreen(new ConfigScreen(screen)));
        fullscreenOption.setTooltip(minecraft.font.split(tooltip, 200));

        optionsRowList.addBig(fullscreenOption);
        List<OptionsRowList.Row> widgets = optionsRowList.children();
        OptionsRowList.Row lastElement = widgets.remove(widgets.size() - 1);
        widgets.add(0, lastElement);
        log.info("Added Borderless Window Config Screen to OptionsRowList");
    }
}
