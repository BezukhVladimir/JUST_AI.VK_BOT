package dev.bezukh.vkbot.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RandomIdGeneratorTest {
    @Test
    fun `generateRandomId should return unique values for 100 calls`() {
        // Arrange
        val uniqueIds = mutableSetOf<Int>()

        // Act
        repeat(100) {
            uniqueIds.add(RandomIdGenerator.generateRandomId())
        }

        // Assert
        assertThat(uniqueIds)
            .hasSize(100)
            .doesNotContainNull()
    }
}
