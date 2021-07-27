package de.nekeras.borderless.util;

import net.minecraft.network.chat.Component;

/**
 * Indicates a type which may be translated by a {@link Component}.
 */
public interface Translatable {

    /**
     * The localization message for this instance.
     *
     * @return The message as an {@link Component}.
     */
    Component getTranslation();
}
