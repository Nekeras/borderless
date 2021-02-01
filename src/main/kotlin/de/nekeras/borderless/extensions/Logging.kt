package de.nekeras.borderless.extensions

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : Any> logger(): LoggerDelegate<T> = LoggerDelegate()

class LoggerDelegate<in T : Any> : ReadOnlyProperty<T, Logger> {

    override fun getValue(thisRef: T, property: KProperty<*>): Logger =
        LogManager.getLogger(retrieveLoggingClass(thisRef))

    private fun retrieveLoggingClass(thisRef: T): Class<*> =
        thisRef::class.let { if (it.isCompanion) it.java.enclosingClass else it.java }
}
