package de.nekeras.borderless

import net.minecraft.client.Minecraft
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

/**
 * Common class for the Borderless Window Minecraft mod.
 */
@Mod(BorderlessWindow.MOD_ID)
class BorderlessWindow {

    /**
     * A holder which stores an instance of the current [FullscreenModeHolder] for the main
     * window.
     */
    lateinit var fullscreenModeHolder: FullscreenModeHolder
        private set

    init {
        instance = this
    }

    @SubscribeEvent
    fun onClientInit(event: FMLClientSetupEvent) {
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

        /**
         * The current mod instance. Workaround for Kotlin objects, as Forge is not able to work
         * with Kotlin objects.
         */
        lateinit var instance: BorderlessWindow
            @JvmStatic get
            private set

    }

}
