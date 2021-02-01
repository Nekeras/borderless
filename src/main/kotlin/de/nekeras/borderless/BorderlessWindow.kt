package de.nekeras.borderless

import de.nekeras.borderless.extensions.logger
import net.minecraft.client.Minecraft
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

/**
 * Common class for the Borderless Window Minecraft mod.
 */
@Mod(BorderlessWindow.MOD_ID)
class BorderlessWindow {

    /**
     * A holder which stores an instance of the current [FullscreenModeHolder] for the main
     * window.
     */
    private lateinit var fullscreenModeHolder: FullscreenModeHolder

    init {
        log.info("Creating mod instance")

        instance = this

        FMLJavaModLoadingContext.get().modEventBus.addListener(
            EventPriority.NORMAL,
            false,
            FMLClientSetupEvent::class.java,
            this::onClientInit
        )
    }

    private fun onClientInit(event: FMLClientSetupEvent) {
        log.info("Enqueuing work to create fullscreen mode holder")

        event.enqueueWork {
            val window = Minecraft.getInstance().mainWindow
            fullscreenModeHolder = FullscreenModeHolder(window)
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
