package de.nekeras.borderless.util;

import net.minecraft.util.text.ITextComponent;

/**
 * Indicates a type which may be translated by a {@link ITextComponent}.
 */
public interface Translatable {

    /**
     * The localization message for this instance.
     *
     * @return The message as an {@link ITextComponent}.
     */
    ITextComponent getTranslation();
}
