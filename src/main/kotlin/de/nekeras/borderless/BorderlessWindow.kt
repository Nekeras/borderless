package de.nekeras.borderless

import de.nekeras.borderless.client.listener.VideoSettingsListener
import de.nekeras.borderless.config.Config
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.fml.ExtensionPoint
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.network.FMLNetworkConstants
import org.apache.commons.lang3.tuple.Pair
import java.util.function.BiFunction
import java.util.function.BiPredicate
import java.util.function.Supplier

/**
 * Common class for the Borderless Window Minecraft mod.
 */
@Mod(BorderlessWindow.MOD_ID)
class BorderlessWindow {

    init {
        log.info("Creating mod instance")

        instance = this

        FMLJavaModLoadingContext.get().modEventBus.addListener(
            EventPriority.NORMAL,
            false,
            FMLClientSetupEvent::class.java,
            this::onClientInit
        )

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, VideoSettingsListener::onVideoSettings)

        configureModLoadingContext()
    }

    private fun configureModLoadingContext() = with(ModLoadingContext.get()) {
        log.info("Enable server compatibility")
        registerExtensionPoint(ExtensionPoint.DISPLAYTEST) {
            Pair.of(
                Supplier { FMLNetworkConstants.IGNORESERVERONLY },
                BiPredicate { _, _ -> true }
            )
        }

        log.info("Register client configuration")
        registerConfig(ModConfig.Type.CLIENT, Config.configSpec)

        log.info("Register client configuration screen")
        registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY) {
            BiFunction { _, modListScreen ->
                de.nekeras.borderless.client.gui.ConfigScreen(modListScreen)
            }
        }
    }

    private fun onClientInit(event: FMLClientSetupEvent) {
        log.info("Enqueuing work to create fullscreen mode holder")
        event.enqueueWork {
            de.nekeras.borderless.client.FullscreenModeHolder.initializeMinecraft()
        }
    }

    companion object {

        /**
         * The mod id for this Forge mod.
         */
        const val MOD_ID = "borderless"

        private val log by logger()

        /**
         * The current mod instance. Workaround for Kotlin objects, as Forge is not able to work
         * with Kotlin objects.
         */
        lateinit var instance: BorderlessWindow
            @JvmStatic get
            private set
    }
}
