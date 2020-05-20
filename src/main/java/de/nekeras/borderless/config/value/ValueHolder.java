package de.nekeras.borderless.config.value;

/**
 * Abstraction of a single configuration value that can be set and retrieved using this interface.
 *
 * @param <T> The type of value
 */
public interface ValueHolder<T> {

    /**
     * Sets the value.
     *
     * @param t The new value
     */
    void set(T t);

    /**
     * Gets the value.
     *
     * @return The current value
     */
    T get();

}
