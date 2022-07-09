package de.nekeras.borderless.client.listener;

import de.nekeras.borderless.BorderlessWindow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BorderlessWindow.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VideoSettingsListener {
//        TODO re-add config button
//
//    private static final String TITLE_KEY = "borderless.config.video_settings_button";
//    private static final Logger log = LogManager.getLogger();
//
//    @SubscribeEvent
//    public static void onVideoSettings(@Nonnull ScreenEvent.Init.Post event) {
//        if (event.getScreen() instanceof VideoSettingsScreen screen) {
//            log.info("Opened VideoSettingsScreen");
//
//            ReflectionUtils.getOptionsList(screen).ifPresent(optionsList -> addToOptionsList(screen, optionsList));
//        }
//    }
//
//    private static void addToOptionsList(@Nonnull VideoSettingsScreen screen, @Nonnull OptionsList optionsRowList) {
//        log.info("Found OptionsList");
//        Minecraft minecraft = Minecraft.getInstance();
//
//
//        optionsRowList.addBig(fullscreenOption);
//        moveLastEntryToStart(optionsRowList.children());
//        log.info("Added Borderless Window Config Screen to OptionsList");
//    }
//
//    private static <T> void moveLastEntryToStart(List<T> list) {
//        if (list.isEmpty()) {
//            return;
//        }
//
//        T lastElement = list.remove(list.size() - 1);
//        list.add(0, lastElement);
//    }
}
