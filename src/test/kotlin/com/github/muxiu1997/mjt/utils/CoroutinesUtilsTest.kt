package com.github.muxiu1997.mjt.utils

import com.github.muxiu1997.mjt.utils.CoroutinesUtils.asyncMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import java.time.Duration

internal class CoroutinesUtilsTest {
    @Test
    fun asyncMap_default() {
        val delaySeconds = Duration.ofSeconds(1)
        val list = listOf(1, 2, 3)
        val result = list.asyncMap {
            delay(delaySeconds.toMillis())
            it * 2
        }
        assertTimeout(delaySeconds.multipliedBy(list.size.toLong())) {
            assertEquals(listOf(2, 4, 6), result)
        }
    }

    @Test
    fun asyncMap_IO() {
        val delaySeconds = Duration.ofSeconds(1)
        val list = listOf(1, 2, 3)
        val result = list.asyncMap(Dispatchers.IO) {
            Thread.sleep(delaySeconds.toMillis())
            it * 2
        }
        assertTimeout(delaySeconds.multipliedBy(list.size.toLong())) {
            assertEquals(listOf(2, 4, 6), result)
        }
    }
}
