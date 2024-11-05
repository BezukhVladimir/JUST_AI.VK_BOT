package dev.bezukh.vkbot.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @see <a href=https://dev.vk.com/ru/api/community-events/json-schema#message_new>Message New (json-schema)</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageNew(
    @JsonProperty("message")
    val message: Message
) : CallbackObject()
