package de.nekeras.borderless;

import de.nekeras.borderless.config.Config;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

/**
 * The main Forge mod class.
 */
@Mod(Borderless.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class Borderless {

    /**
     * The mod id of this Forge mod.
     */
    public static final String MOD_ID = "borderless";

    private static final Logger log = LogManager.getLogger();

    public Borderless() {
        ModLoadingContext context = ModLoadingContext.get();

        // Client dist only, make sure server is always compatible with this mod
        context.registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(
                () -> FMLNetworkConstants.IGNORESERVERONLY,
                (a, b) -> true));

        // Register the config
        context.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);
    }

    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(@Nullable FMLClientSetupEvent event) {
        ModLoadingContext context = ModLoadingContext.get();

        // Config registration
        context.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, modListScreen) -> new de.nekeras.borderless.config.gui.ConfigScreen(modListScreen));

        log.info("Enqueue initialization work to main thread");
        DeferredWorkQueue.runLater(FullscreenModeHolder::initMinecraft);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onConfigReload(ModConfig.Reloading event) {
        de.nekeras.borderless.FullscreenModeHolder.setFullscreenMode(
                de.nekeras.borderless.fullscreen.FullscreenMode.fromConfig());
    }
}
