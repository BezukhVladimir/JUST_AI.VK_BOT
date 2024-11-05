package dev.bezukh.vkbot.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.bezukh.vkbot.deserializer.CallbackRequestDeserializer

/**
 * @see <a href=https://dev.vk.com/ru/api/community-events/json-schema>Callback Request (json-schema)</a>
 */
@JsonDeserialize(using = CallbackRequestDeserializer::class)
data class CallbackRequest(
    val type: String,
    val `object`: CallbackObject?,

    @JsonProperty("group_id")
    val groupId: Int
)
