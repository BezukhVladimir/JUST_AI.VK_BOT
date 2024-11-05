package dev.bezukh.vkbot.controller

import dev.bezukh.vkbot.handler.EventHandlerRegistry
import dev.bezukh.vkbot.model.CallbackRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for handling VK bot callbacks.
 */
@RestController
@RequestMapping("/vkbot")
class CallbackController(
    private val eventHandlerRegistry: EventHandlerRegistry
) {
    private val logger: Logger = LoggerFactory.getLogger(CallbackController::class.java)

    /**
     * Handles incoming VK callback events.
     *
     * @param request the callback request received from VK
     * @return ResponseEntity with appropriate response
     */
    @PostMapping
    fun handleEvent(@RequestBody request: CallbackRequest): ResponseEntity<String> {
        logger.info("Received event of type: ${request.type} for group ID: ${request.groupId}")

        val handler = eventHandlerRegistry.getHandler(request.type)

        return if (handler != null) {
            logger.debug("Handler found for type: ${request.type}")
            handler.handle(request)
        } else {
            logger.warn("Unsupported event type received: ${request.type}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported event type")
        }
    }
}
