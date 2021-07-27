package de.nekeras.borderless.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

/**
 * Supported window attributes that are used by Borderless Window for the fullscreen display modes.
 */
@OnlyIn(Dist.CLIENT)
public enum GlfwWindowAttribute {

    DECORATED(GLFW.GLFW_DECORATED, true),
    AUTO_ICONIFY(GLFW.GLFW_AUTO_ICONIFY, true);

    private final int bit;
    private final boolean enabledByDefault;

    GlfwWindowAttribute(int bit, boolean enabledByDefault) {
        this.bit = bit;
        this.enabledByDefault = enabledByDefault;
    }

    /**
     * The GLFW bit used for unique identification.
     *
     * @return The bit value
     */
    public int getBit() {
        return bit;
    }

    /**
     * Whether the attribute is enabled by default.
     *
     * @return The default value
     */
    public boolean isEnabledByDefault() {
        return enabledByDefault;
    }
}
