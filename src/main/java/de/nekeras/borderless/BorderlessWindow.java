package de.nekeras.borderless;

import de.nekeras.borderless.client.gui.ConfigScreen;
import de.nekeras.borderless.config.Config;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

/**
 * The main Forge mod class.
 */
@Mod(BorderlessWindow.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class BorderlessWindow {

    /**
     * The mod id of this Forge mod.
     */
    public static final String MOD_ID = "borderlesswindow";

    private static final Logger log = LogManager.getLogger();

    public BorderlessWindow() {
        log.info("Creating mod instance");
        ModLoadingContext context = ModLoadingContext.get();

        log.info("Enable server compatibility");
        context.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        log.info("Register client configuration");
        context.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(@Nonnull FMLClientSetupEvent event) {
        log.info("Initializing from client context");
        ModLoadingContext context = ModLoadingContext.get();

        log.info("Register client configuration screen");
        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((mc, modListScreen) -> new ConfigScreen(modListScreen)));

        log.info("Enqueue initialization work to main thread");
        event.enqueueWork(de.nekeras.borderless.client.FullscreenDisplayModeHolder::initMinecraft);
    }
}
