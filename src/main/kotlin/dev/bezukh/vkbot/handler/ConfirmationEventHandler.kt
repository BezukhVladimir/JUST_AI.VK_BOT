package dev.bezukh.vkbot.handler

import dev.bezukh.vkbot.model.CallbackRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Event handler for confirmation events.
 *
 * @property confirmationToken the confirmation token required for VK Callback API.
 *
 * @see <a href=https://dev.vk.com/ru/api/callback/getting-started#Подключение%20Callback%20API>Connecting Callback API</a>
 */
@Component
class ConfirmationEventHandler : EventHandler {

    @Value("\${vk.confirmation-token}")
    private lateinit var confirmationToken: String

    override fun handle(request: CallbackRequest): ResponseEntity<String> {
        return ResponseEntity.ok(confirmationToken)
    }

    override fun supports(type: String): Boolean {
        return type == "confirmation"
    }
}
