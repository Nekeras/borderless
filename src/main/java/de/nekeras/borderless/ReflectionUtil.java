package de.nekeras.borderless;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Function;

import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.IWindowEventListener;

/**
 * Minecraft native code reflection util for easier access of some fields and methods.
 */
public class ReflectionUtil {

    /**
     * Updates the {@link IWindowEventListener} in the {@link MainWindow} instance with a custom
     * window event listener.
     *
     * @param window The main window instance that should be updated
     * @param updateSupplier A function that accepts the original value of the
     *                      {@link IWindowEventListener} field in the {@link MainWindow} instance
     *                       and returns a new value that should be assigned
     */
    public static void updateWindowEventListener(MainWindow window,
        Function<IWindowEventListener, IWindowEventListener> updateSupplier) {

        Field oldListenerField = Arrays.stream(MainWindow.class.getDeclaredFields())
            .filter(field -> field.getType() == IWindowEventListener.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "Could not find event listener, is this the correct Minecraft version?"));

        try {
            oldListenerField.setAccessible(true);
            IWindowEventListener oldListener = (IWindowEventListener) oldListenerField.get(window);
            oldListenerField.set(window, updateSupplier.apply(oldListener));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not access or update old listener", e);
        }
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

}
