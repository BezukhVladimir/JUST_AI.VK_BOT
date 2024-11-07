package dev.bezukh.vkbot.service

import dev.bezukh.vkbot.utils.RandomIdGenerator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

/**
 * Service for handling messages with VK API.
 *
 * @property accessToken the VK access token.
 * @property webClient WebClient instance for making requests.
 */
@Service
class MessageService(
    @Value("\${vk.access-token}") private val accessToken: String,
    private val webClient: WebClient
) {
    private val logger = LoggerFactory.getLogger(MessageService::class.java)

    /**
     * Calls a specified VK API method with parameters.
     *
     * @param method the API method to call.
     * @param params the parameters for the method.
     * @return a Mono with the API response as a Map.
     */
    private fun callApiMethod(method: String, params: Map<String, Any>): Mono<Map<String, Any>> {
        val allParams = params + mapOf(
            "access_token" to accessToken,
            "v" to "5.199"
        )

        val uri = UriComponentsBuilder.fromPath(method)
            .apply {
                allParams.forEach { (key, value) ->
                    queryParam(key, value)
                }
            }
            .build()
            .toUriString()

        logger.info("Calling API method: $method with params: $allParams")

        return webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(Map::class.java)
            .map { it as Map<String, Any> }
            .doOnSuccess { response ->
                logger.info("Received response: $response")
            }
            .doOnError { e ->
                logger.error("Error when calling the method $method: ${e.message}", e)
            }
    }

    /**
     * Sends a message to a specific peer on VK.
     *
     * @param peerId the ID of the peer to send the message to.
     * @param message the message to send.
     * @return a Mono with the API response as a Map.
     *
     * @see <a href=https://dev.vk.com/ru/method/messages.send>Messages.send (method)</a>
     */
    fun sendMessage(peerId: Int, message: String): Mono<Map<String, Any>> {
        val params = mapOf(
            "peer_id" to peerId,
            "message" to message,
            "random_id" to RandomIdGenerator.generateRandomId()
        )

        return callApiMethod("messages.send", params)
    }
}
