package de.nekeras.borderless.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import de.nekeras.borderless.util.AccessibleFieldDelegate;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * Minecraft native code reflection util for easier access of some fields and methods.
 */
@OnlyIn(Dist.CLIENT)
public final class ReflectionUtils {

    private static final AccessibleFieldDelegate<Window, WindowEventHandler> windowEventListenerAccessor =
            makeFieldAccessible(Window.class, WindowEventHandler.class);
    private static final AccessibleFieldDelegate<VideoSettingsScreen, OptionsList> optionsListAccessor =
            tryMakeFieldAccessible(VideoSettingsScreen.class, OptionsList.class, thisRef -> null);

    private ReflectionUtils() {
    }

    /**
     * Makes a field of type <code>T</code> in object <code>thisRef</code> accessible through reflection.
     *
     * @param inObject  The object to lookup for the field
     * @param fieldType The field to access
     * @throws IllegalStateException If there is no such field of type <code>F</code>
     */
    @SuppressWarnings("SameParameterValue") // might be needed in future
    @Nonnull
    private static <C, F> AccessibleFieldDelegate<C, F> makeFieldAccessible(@Nonnull Class<C> inClass, @Nonnull Class<F> fieldType) {
        try {
            return new AccessibleFieldDelegate<>(inClass, fieldType);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(String.format("Expected field of type %s in class %s",
                    inClass.getName(), fieldType.getName()), e);
        }
    }

    /**
     * Makes a field of type <code>T</code> in object <code>thisRef</code> accessible through reflection.
     *
     * @param inObject        The object to lookup for the field
     * @param fieldType       The field to access
     * @param defaultSupplier The supplier which is called in when retrieving the value if the field was not found
     */
    @SuppressWarnings("SameParameterValue") // might be needed in future
    @Nonnull
    private static <C, F> AccessibleFieldDelegate<C, F> tryMakeFieldAccessible(@Nonnull Class<C> inClass, @Nonnull Class<F> fieldType, @Nonnull Function<C, F> defaultSupplier) {
        return new AccessibleFieldDelegate<>(inClass, fieldType, defaultSupplier);
    }

    /**
     * Checks if this method was called by either the {@link GLFWFramebufferSizeCallbackI} or
     * {@link GLFWWindowSizeCallbackI}.
     *
     * @return <code>true</code> if called by a GLFW callback, otherwise <code>false</code>
     */
    public static boolean isCalledByGlfwCallback() {
        return Arrays.stream(Thread.currentThread().getStackTrace()).anyMatch(e ->
                e.getClassName().equals(GLFWFramebufferSizeCallbackI.class.getName())
                        || e.getClassName().equals(GLFWWindowSizeCallbackI.class.getName()));
    }

    /**
     * Updates the {@link IWindowEventListener} in the {@link Window} instance with a custom
     * window event listener.
     *
     * @param window         The window to update the listener for
     * @param updateSupplier A function that accepts the original value of the
     *                       {@link IWindowEventListener} field in the {@link Window} instance
     *                       and returns a new value that should be assigned
     */
    public static void updateWindowEventListener(@Nonnull Window window, @Nonnull Function<WindowEventHandler, WindowEventHandler> updateSupplier) {
        WindowEventHandler oldListener = windowEventListenerAccessor.getValue(window);
        WindowEventHandler newListener = updateSupplier.apply(oldListener);
        windowEventListenerAccessor.setValue(window, newListener);
    }

    /**
     * Retrieves the <code>optionsRowList</code> field in the {@link VideoSettingsScreen}, if available. If might become
     * unavailable due to OptiFine or some other mod replacing the original {@link VideoSettingsScreen}.
     *
     * @param screen The screen
     * @return The options row list if it exists
     */
    @Nonnull
    public static Optional<OptionsList> getOptionsList(@Nonnull VideoSettingsScreen screen) {
        return Optional.ofNullable(optionsListAccessor.getValue(screen));
    }
}
