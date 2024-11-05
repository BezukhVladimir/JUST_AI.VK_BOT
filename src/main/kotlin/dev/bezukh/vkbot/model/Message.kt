package dev.bezukh.vkbot.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @see <a href=https://dev.vk.com/ru/reference/objects/message>Message (object)</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
    @JsonProperty("peer_id")
    val peerId: Int,

    @JsonProperty("text")
    val text: String
)
