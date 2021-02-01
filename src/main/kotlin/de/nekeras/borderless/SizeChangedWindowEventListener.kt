package de.nekeras.borderless

import de.nekeras.borderless.extensions.logger
import net.minecraft.client.renderer.IWindowEventListener
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI
import org.lwjgl.glfw.GLFWWindowSizeCallbackI

/**
 * A [IWindowEventListener] that listens for window resizes or fullscreen mode updates. It will then
 * notify the supplied [onSizeChanged] callback. Furthermore, this listener may have a [parent],
 * which means it will notify the parent on all events as well.
 */
class SizeChangedWindowEventListener(
    private val parent: IWindowEventListener?,
    private val onSizeChanged: () -> Unit
) : IWindowEventListener {

    override fun setGameFocused(focused: Boolean) {
        log.info("Game focus changed. Now focused: $focused")

        parent?.setGameFocused(focused)
    }

    override fun updateWindowSize() {
        val isCallback = Thread.currentThread().isCalledByGlfwCallback()

        parent?.updateWindowSize()

        if (!isCallback) {
            log.info("Window size updated")
            onSizeChanged()
        }
    }

    override fun ignoreFirstMove() {
        parent?.ignoreFirstMove()
    }

    companion object {

        private val log by logger()

        /**
         * Validates whether the current call stack is called by either
         * [GLFWFramebufferSizeCallbackI] or [GLFWWindowSizeCallbackI].
         */
        private fun Thread.isCalledByGlfwCallback(): Boolean =
            stackTrace.any { element ->
                when (element.className) {
                    GLFWFramebufferSizeCallbackI::class.qualifiedName -> true
                    GLFWWindowSizeCallbackI::class.qualifiedName -> true
                    else -> false
                }
            }
    }
}
