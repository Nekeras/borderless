package de.nekeras.borderless.client;

import de.nekeras.borderless.util.AccessibleFieldDelegate;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.renderer.IWindowEventListener;
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

    private static final AccessibleFieldDelegate<MainWindow, IWindowEventListener> windowEventListenerAccessor =
            makeFieldAccessible(MainWindow.class, IWindowEventListener.class);
    private static final AccessibleFieldDelegate<VideoSettingsScreen, OptionsRowList> optionsRowListAccessor =
            tryMakeFieldAccessible(VideoSettingsScreen.class, OptionsRowList.class, thisRef -> null);

    private ReflectionUtils() {
    }

    /**
     * Makes a field of type <code>T</code> in object <code>thisRef</code> accessible through reflection.
     *
     * @param inObject  The object to lookup for the field
     * @param fieldType The field to access
     * @throws IllegalStateException If there is no such field of type <code>F</code>
     */
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
    @Nonnull
    private static <C, F> AccessibleFieldDelegate<C, F> tryMakeFieldAccessible(@Nonnull Class<C> inClass, @Nonnull Class<F> fieldType, @Nonnull Function<C, F> defaultSupplier) {
        return new AccessibleFieldDelegate<C, F>(inClass, fieldType, defaultSupplier);
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
     * Updates the {@link IWindowEventListener} in the {@link MainWindow} instance with a custom
     * window event listener.
     *
     * @param window         The window to update the listener for
     * @param updateSupplier A function that accepts the original value of the
     *                       {@link IWindowEventListener} field in the {@link MainWindow} instance
     *                       and returns a new value that should be assigned
     */
    public static void updateWindowEventListener(@Nonnull MainWindow window, @Nonnull Function<IWindowEventListener, IWindowEventListener> updateSupplier) {
        IWindowEventListener oldListener = windowEventListenerAccessor.getValue(window);
        IWindowEventListener newListener = updateSupplier.apply(oldListener);
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
    public static Optional<OptionsRowList> getOptionsRowList(@Nonnull VideoSettingsScreen screen) {
        return Optional.ofNullable(optionsRowListAccessor.getValue(screen));
    }
}
