package dev.bezukh.vkbot.utils

/**
 * Utility class for generating random IDs.
 */
object RandomIdGenerator {

    /**
     * Generates a random ID.
     *
     * @return a random integer ID.
     *
     * @see <a href=https://dev.vk.com/ru/method/messages.send>Messages.send (method)</a>
     */
    fun generateRandomId(): Int {
        return (1..Int.MAX_VALUE).random()
    }
}
