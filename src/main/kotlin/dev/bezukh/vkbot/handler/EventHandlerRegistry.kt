package dev.bezukh.vkbot.handler

import org.springframework.stereotype.Service

/**
 * Registry for finding appropriate handlers for VK events.
 *
 * @property handlers list of available event handlers.
 */
@Service
class EventHandlerRegistry(
    private val handlers: List<EventHandler>
) {
    /**
     * Finds an event handler that supports the given event type.
     *
     * @param type the type of the event.
     * @return the supporting EventHandler or null if not found.
     */
    fun getHandler(type: String): EventHandler? {
        return handlers.find { it.supports(type) }
    }
}
