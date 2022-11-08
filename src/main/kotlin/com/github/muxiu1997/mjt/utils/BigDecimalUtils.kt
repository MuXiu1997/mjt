package com.github.muxiu1997.mjt.utils

import org.jetbrains.annotations.Contract
import java.math.BigDecimal
import java.math.RoundingMode

object BigDecimalUtils {
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

    @JvmStatic
    @Contract(pure = true)
    fun getBigDecimalOrZero(value: Any?): BigDecimal = value.toBigDecimalOrZero()

    @JvmStatic
    @Contract(pure = true)
    fun safeAdd(a: Any?, b: Any?): BigDecimal = a.toBigDecimalOrZero() + b.toBigDecimalOrZero()

    @JvmStatic
    @Contract(pure = true)
    fun safeSub(a: Any?, b: Any?): BigDecimal = a.toBigDecimalOrZero() - b.toBigDecimalOrZero()

    @JvmStatic
    @Contract(pure = true)
    fun safeMul(a: Any?, b: Any?): BigDecimal = a.toBigDecimalOrZero() * b.toBigDecimalOrZero()

    @JvmStatic
    @Contract(pure = true)
    fun safeDiv(a: Any?, b: Any?, scale: Int, roundingMode: RoundingMode): BigDecimal = try {
        a.toBigDecimalOrZero().divide(b.toBigDecimalOrZero(), scale, roundingMode)
    } catch (ignored: Exception) {
        BigDecimal.ZERO.setScale(scale)
    }

    @JvmStatic
    @Contract(pure = true)
    fun safeDiv(a: Any?, b: Any?, scale: Int): BigDecimal = safeDiv(a, b, scale, RoundingMode.HALF_UP)

    @JvmStatic
    @Contract(pure = true)
    fun safeDiv(a: Any?, b: Any?): BigDecimal = safeDiv(a, b, 2)

    @JvmStatic
    @Contract(pure = true)
    fun safeSum(vararg nums: Any?): BigDecimal = nums.map { it.toBigDecimalOrZero() }.reduce(this::safeAdd)
}

