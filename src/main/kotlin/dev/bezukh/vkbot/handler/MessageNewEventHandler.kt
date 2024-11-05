package dev.bezukh.vkbot.handler

import dev.bezukh.vkbot.model.CallbackRequest
import dev.bezukh.vkbot.model.MessageNew
import dev.bezukh.vkbot.service.MessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Event handler for processing new messages from VK.
 *
 * @property messageService service for sending messages to VK.
 *
 * @see <a href=https://dev.vk.com/ru/api/community-events/json-schema#message_new>Message New (json-schema)</a>
 */
@Component
class MessageNewEventHandler(
    private val messageService: MessageService
) : EventHandler {
    private val logger: Logger = LoggerFactory.getLogger(MessageNewEventHandler::class.java)

    override fun handle(request: CallbackRequest): ResponseEntity<String> {
        val message = (request.`object` as? MessageNew)?.message

        message?.let {
            val peerId = it.peerId
            val responseMessage = "Вы сказали: ${it.text}"

            messageService.sendMessage(peerId, responseMessage)
                .doOnSuccess {
                    logger.info("Message successfully sent to $peerId: $responseMessage")
                }
                .doOnError { error ->
                    logger.error("Error sending message to $peerId: ${error.message}", error)
                }
                .subscribe()
        }

        return ResponseEntity.ok("ok")
    }

    override fun supports(type: String): Boolean {
        return type == "message_new"
    }
}
