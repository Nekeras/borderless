package de.nekeras.borderless.config

import de.nekeras.borderless.Translatable

interface ConfigEnum : Translatable {

    /**
     * The value's unlocalized name.
     */
    val name: String

    /**
     * An unlocalized comment describing this value.
     */
    val comment: String
}
