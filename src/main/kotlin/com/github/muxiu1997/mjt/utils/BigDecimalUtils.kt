package com.github.muxiu1997.mjt.utils

import org.jetbrains.annotations.Contract
import java.math.BigDecimal
import java.math.RoundingMode

object BigDecimalUtils {
    /**
     * Convert an object to [BigDecimal], return [BigDecimal.ZERO] if the object is null or cannot be converted to [BigDecimal]
     */
    @Contract(pure = true)
    private fun Any?.toBigDecimalOrZero(): BigDecimal = try {
        when (this) {
            is BigDecimal -> this
            is Number -> BigDecimal(this.toString())
            else -> BigDecimal(this.toString())
        }
    } catch (ignored: Exception) {
        BigDecimal.ZERO
    }

    /**
     * Convert an object to [BigDecimal], return [BigDecimal.ZERO] if the object is null or cannot be converted to [BigDecimal]
     * @see toBigDecimalOrZero
     */
    @JvmStatic
    @Contract(pure = true)
    fun getBigDecimalOrZero(`object`: Any?): BigDecimal = `object`.toBigDecimalOrZero()

    /**
     * Returns a [BigDecimal] whose value is (a + b)
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     */
    @JvmStatic
    @Contract(pure = true)
    infix fun Any?.safeAdd(other: Any?): BigDecimal = this.toBigDecimalOrZero() + other.toBigDecimalOrZero()

    /**
     * Returns a [BigDecimal] whose value is (a - b)
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     */
    @JvmStatic
    @Contract(pure = true)
    infix fun Any?.safeSub(other: Any?): BigDecimal = this.toBigDecimalOrZero() - other.toBigDecimalOrZero()

    /**
     * Returns a [BigDecimal] whose value is (a * b)
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     */
    @JvmStatic
    @Contract(pure = true)
    infix fun Any?.safeMul(other: Any?): BigDecimal = this.toBigDecimalOrZero() * other.toBigDecimalOrZero()

    /**
     * Returns a [BigDecimal] whose value is (a / b)
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     */
    @JvmName("_safeDiv")
    @JvmStatic
    @Contract(pure = true)
    infix fun Any?.safeDiv(other: Any?): BigDecimal = safeDiv(this, other)

    /**
     * Returns a [BigDecimal] whose value is (a / b)
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     */
    @JvmStatic
    @JvmOverloads
    @Contract(pure = true)
    fun safeDiv(a: Any?, b: Any?, scale: Int = 2, roundingMode: RoundingMode = RoundingMode.HALF_UP): BigDecimal = try {
        a.toBigDecimalOrZero().divide(b.toBigDecimalOrZero(), scale, roundingMode)
    } catch (ignored: Exception) {
        BigDecimal.ZERO.setScale(scale)
    }

    /**
     * Returns a [BigDecimal] whose value is the sum of the objects
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     * @see safeAdd
     */
    @JvmStatic
    @Contract(pure = true)
    fun safeSum(vararg objects: Any?): BigDecimal = objects.fold(BigDecimal.ZERO) { a, b -> a safeAdd b }

    /**
     * Returns a [BigDecimal] whose value is the sum of the objects in the collection
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     * @see safeAdd
     */
    @JvmStatic
    @Contract(pure = true)
    fun <T> Iterable<T>?.safeSum(): BigDecimal {
        return this?.fold(BigDecimal.ZERO) { a, b -> a safeAdd b } ?: BigDecimal.ZERO
    }

    /**
     * Returns a [BigDecimal] whose value is the sum of the objects in the sequence
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO].
     * @see toBigDecimalOrZero
     * @see safeAdd
     */
    @JvmStatic
    @Contract(pure = true)
    fun <T> Sequence<T>?.safeSum(): BigDecimal {
        return this?.fold(BigDecimal.ZERO) { a, b -> a safeAdd b } ?: BigDecimal.ZERO
    }


    /**
     * Returns a [BigDecimal] whose value is sum of all values produced by [selector] function applied to each element in the collection
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO]
     * @see toBigDecimalOrZero
     * @see safeAdd
     */
    @JvmStatic
    @Contract(pure = true)
    fun <T> Iterable<T>?.safeSumOf(selector: (T) -> Any?): BigDecimal {
        return this?.map(selector)?.fold(BigDecimal.ZERO) { a, b -> a safeAdd b } ?: BigDecimal.ZERO
    }

    /**
     * Returns a [BigDecimal] whose value is sum of all values produced by [selector] function applied to each element in the sequence
     *
     * objects that cannot be converted to [BigDecimal] will be converted to [BigDecimal.ZERO]
     * @see toBigDecimalOrZero
     * @see safeAdd
     */
    @JvmStatic
    @Contract(pure = true)
    fun <T> Sequence<T>?.safeSumOf(selector: (T) -> Any?): BigDecimal {
        return this?.map(selector)?.fold(BigDecimal.ZERO) { a, b -> a safeAdd b } ?: BigDecimal.ZERO
    }
}
