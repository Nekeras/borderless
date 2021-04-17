package de.nekeras.borderless

import net.minecraft.util.text.ITextComponent

/**
 * Indicates a type which can be translated.
 */
interface Translatable {

    /**
     * The localization message for this instance.
     */
    val translation: ITextComponent
}
