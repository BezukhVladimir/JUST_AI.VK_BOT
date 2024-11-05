package dev.bezukh.vkbot.controller

import dev.bezukh.vkbot.handler.ConfirmationEventHandler
import dev.bezukh.vkbot.handler.EventHandlerRegistry
import dev.bezukh.vkbot.handler.MessageNewEventHandler
import dev.bezukh.vkbot.model.CallbackRequest
import dev.bezukh.vkbot.model.Message
import dev.bezukh.vkbot.model.MessageNew
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CallbackControllerTest {

    private lateinit var callbackController: CallbackController

    private val eventHandlerRegistry: EventHandlerRegistry = mockk()
    private val confirmationEventHandler: ConfirmationEventHandler = mockk()
    private val messageNewEventHandler: MessageNewEventHandler = mockk()

    @BeforeEach
    fun setUp() {
        callbackController = CallbackController(eventHandlerRegistry)
    }

    @Test
    fun `should return OK for confirmation event type`() {
        // Arrange
        val callbackRequest = CallbackRequest(type = "confirmation", `object` = null, groupId = 1)

        every { eventHandlerRegistry.getHandler("confirmation") } returns confirmationEventHandler
        every { confirmationEventHandler.handle(any()) } returns ResponseEntity.ok("<your_confirmation_token>")

        // Act
        val response = callbackController.handleEvent(callbackRequest)

        // Assert
        verify { eventHandlerRegistry.getHandler("confirmation") }
        verify { confirmationEventHandler.handle(callbackRequest) }
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("<your_confirmation_token>")
    }

    @Test
    fun `should return OK for supported event type`() {
        // Arrange
        val callbackRequest = CallbackRequest(type = "message_new", `object` = MessageNew(Message(1, "Hello")), groupId = 1)

        every { eventHandlerRegistry.getHandler("message_new") } returns messageNewEventHandler
        every { messageNewEventHandler.handle(any()) } returns ResponseEntity.ok("ok")

        // Act
        val response = callbackController.handleEvent(callbackRequest)

        // Assert
        verify { eventHandlerRegistry.getHandler("message_new") }
        verify { messageNewEventHandler.handle(callbackRequest) }
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("ok")
    }

    @Test
    fun `should return BAD_REQUEST for unsupported event type`() {
        // Arrange
        val callbackRequest = CallbackRequest(type = "unknown_event", `object` = null, groupId = 1)

        every { eventHandlerRegistry.getHandler("unknown_event") } returns null

        // Act
        val response = callbackController.handleEvent(callbackRequest)

        // Assert
        verify { eventHandlerRegistry.getHandler("unknown_event") }
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.body).isEqualTo("Unsupported event type")
    }
}
