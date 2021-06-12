package de.nekeras.borderless

import java.lang.reflect.Field
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Tries to find the first [Field] inside a class [C] of type [F]. If there is no such field of type [F] in this class,
 * this method will throw [NoSuchFieldException]. Supports private/protected fields.
 */
@Throws(NoSuchFieldException::class)
inline fun <reified C : Any, reified F : Any?> findFieldOfType(): Field =
    C::class.java.declaredFields.find { field ->
        field.type == F::class.java
    }?.let { field ->
        field.isAccessible = true
        field
    } ?: throw NoSuchFieldException("Could not find field of type ${F::class} in class ${C::class}")

/**
 * Makes a field inside a class [C] of type [F] accessible through a [ReadWriteProperty] with getter and setter
 * functions. If there is no such field of type [F] in this class, this method will throw [NoSuchFieldException].
 * Supports private/protected fields.
 */
@Throws(NoSuchFieldException::class)
inline fun <reified C : Any, reified F : Any?> makeFieldAccessible(): AccessibleField<C, F> =
    AccessibleField(findFieldOfType<C, F>())

@JvmInline
value class AccessibleField<C : Any, F : Any?>(private val field: Field) : ReadWriteProperty<C, F> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: C, property: KProperty<*>): F = field.get(thisRef) as F

    override fun setValue(thisRef: C, property: KProperty<*>, value: F) {
        field.set(thisRef, value)
    }
}
