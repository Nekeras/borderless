package de.nekeras.borderless.client.listener;

import de.nekeras.borderless.BorderlessWindow;
import de.nekeras.borderless.client.ReflectionUtils;
import de.nekeras.borderless.client.gui.ButtonOption;
import de.nekeras.borderless.client.gui.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
    private static final Component tooltip = new TranslatableComponent("borderless.config.video_settings_button.tooltip");
    private static final Logger log = LogManager.getLogger();

    @SubscribeEvent
    public static void onVideoSettings(@Nonnull GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof VideoSettingsScreen screen) {
            log.info("Opened VideoSettingsScreen");

            ReflectionUtils.getOptionsList(screen).ifPresent(optionsList -> addToOptionsList(screen, optionsList));
        }
    }

    private static void addToOptionsList(@Nonnull VideoSettingsScreen screen, @Nonnull OptionsList optionsRowList) {
        log.info("Found OptionsList");
        Minecraft minecraft = Minecraft.getInstance();

        ButtonOption fullscreenOption = new ButtonOption(TITLE_KEY,
                btn -> minecraft.setScreen(new ConfigScreen(screen)));

        optionsRowList.addBig(fullscreenOption);
        moveLastEntryToStart(optionsRowList.children());
        log.info("Added Borderless Window Config Screen to OptionsList");
    }

    private static <T> void moveLastEntryToStart(List<T> list) {
        if (list.isEmpty()) {
            return;
        }

        T lastElement = list.remove(list.size() - 1);
        list.add(0, lastElement);
    }
}
