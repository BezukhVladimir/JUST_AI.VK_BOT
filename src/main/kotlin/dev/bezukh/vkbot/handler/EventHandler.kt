package dev.bezukh.vkbot.handler

import dev.bezukh.vkbot.model.CallbackRequest
import org.springframework.http.ResponseEntity

interface EventHandler {
    fun handle(request: CallbackRequest): ResponseEntity<String>
    fun supports(type: String): Boolean
}
