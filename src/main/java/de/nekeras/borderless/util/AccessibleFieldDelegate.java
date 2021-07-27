package de.nekeras.borderless.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * Makes a field of type <code>F</code> in object <code>thisRef</code> accessible through reflection.
 * It's value may be accessed using {@link #getValue(Object)} and {@link #setValue(Object, Object)}.
 *
 * @param <F> The type of the field to find in the object.
 * @see #AccessibleFieldDelegate(Class, Class)
 * @see #AccessibleFieldDelegate(Class, Class, Function)
 */
public class AccessibleFieldDelegate<C, F> {

    private static final Logger log = LogManager.getLogger();

    private final Field field;
    private Function<C, F> defaultSupplier;

    public AccessibleFieldDelegate(@Nonnull Class<C> inClass, @Nonnull Class<F> fieldClass)
            throws NoSuchFieldException {

        Optional<Field> field = findFieldOfType(inClass, fieldClass);

        if (field.isPresent()) {
            this.field = field.get();
        } else {
            throw new NoSuchFieldException(String.format("Failed to find field of type %s in type %s",
                    fieldClass.getName(), inClass));
        }
    }

    public AccessibleFieldDelegate(@Nonnull Class<C> inClass, @Nonnull Class<F> fieldClass, @Nonnull Function<C, F> defaultSupplier) {
        Optional<Field> field = findFieldOfType(inClass, fieldClass);

        if (field.isPresent()) {
            this.field = field.get();
        } else {
            this.field = null;
            this.defaultSupplier = defaultSupplier;
        }
    }

    /**
     * Retrieves the field's value.
     *
     * @return The field value
     * @throws IllegalStateException If there was an error while accessing the field via reflection
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public F getValue(@Nonnull C thisRef) {
        if (field == null) {
            return defaultSupplier.apply(thisRef);
        } else {
            try {
                return (F) field.get(thisRef);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(String.format("Failed to access field %s in %s",
                        field.getType().getName(), thisRef.getClass().getName()), e);
            }
        }
    }

    /**
     * Updates the field's value.
     *
     * @param value The field value
     * @throws IllegalStateException If there was an error while accessing the field via reflection
     */
    public void setValue(@Nonnull C thisRef, @Nullable F value) {
        if (field != null) {
            try {
                field.set(thisRef, value);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(String.format("Failed to access field %s in %s",
                        field.getType().getName(), thisRef.getClass().getName()), e);
            }
        }
    }

    /**
     * Asserts that a field of type <code>F</code> is present in type <code>C</code> and returns the first matching
     * field.
     *
     * @param inClass   The class to check for the field
     * @param fieldType The type of the field
     * @param <C>       The class type
     * @param <F>       The field type
     * @return The field if it was found, otherwise an empty optional
     */
    @Nonnull
    private static <C, F> Optional<Field> findFieldOfType(@Nonnull Class<C> inClass, @Nonnull Class<F> fieldType) {
        Optional<Field> field = Arrays.stream(inClass.getDeclaredFields())
                .filter(f -> f.getType() == fieldType)
                .findFirst();

        if (field.isPresent()) {
            Field resultField = field.get();
            resultField.setAccessible(true);
            return Optional.of(resultField);
        } else {
            return Optional.empty();
        }
    }
}
