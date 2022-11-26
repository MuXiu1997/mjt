package com.github.muxiu1997.mjt.utils

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object CoroutinesUtils {
    /**
     * Returns a list containing the results of async applying the given [transform] function
     * to each element in the original collection.
     *
     * @see [kotlinx.coroutines.async]
     * @see [kotlinx.coroutines.awaitAll]
     */
    @JvmStatic
    @JvmName("asyncMap")
    fun <T, R> Iterable<T>.asyncMap(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        transform: suspend (T) -> R,
    ): List<R> = runBlocking {
        map { async(context, start) { transform(it) } }.awaitAll()
    }
}
