package dev.bezukh.vkbot.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.bezukh.vkbot.model.CallbackObject
import dev.bezukh.vkbot.model.CallbackRequest
import dev.bezukh.vkbot.model.MessageNew
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Custom deserializer for CallbackRequest.
 */
class CallbackRequestDeserializer : JsonDeserializer<CallbackRequest>() {
    private val objectMapper = ObjectMapper()
    private val logger: Logger = LoggerFactory.getLogger(CallbackRequestDeserializer::class.java)

    /**
     * Deserializes JSON into a CallbackRequest object.
     *
     * @param p the JsonParser
     * @param ctxt the DeserializationContext
     * @return deserialized CallbackRequest
     * @throws JsonProcessingException if processing fails
     */
    @Throws(JsonProcessingException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): CallbackRequest {
        val node: JsonNode = p.codec.readTree(p)
        val type = node.get("type").asText()
        val groupId = node.get("group_id").asInt()

        val callbackObject = deserializeCallbackObject(type, node.get("object"))

        return CallbackRequest(type, callbackObject, groupId)
    }

    /**
     * Deserializes the specific callback object based on the event type.
     *
     * @param type the event type as String
     * @param node the JsonNode representing the callback object
     * @return deserialized CallbackObject or null for unhandled types
     * @throws IllegalArgumentException if the type is unsupported
     */
    private fun deserializeCallbackObject(type: String, node: JsonNode?): CallbackObject? {
        logger.debug("Deserializing callback object for type: $type")

        return when (type) {
            "confirmation" -> {
                logger.debug("Event type 'confirmation' received, no object deserialization needed.")
                null
            }
            "message_new" -> {
                objectMapper.convertValue(node, MessageNew::class.java).also {
                    logger.debug("Deserialized MessageNew object: {}", it)
                }
            }
            else -> {
                logger.error("Unknown event type: $type")
                throw IllegalArgumentException("Unknown type: $type")
            }
        }
    }
}
