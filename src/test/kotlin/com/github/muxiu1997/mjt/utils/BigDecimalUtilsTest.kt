package com.github.muxiu1997.mjt.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

internal class BigDecimalUtilsTest {
    private object NumberObject {
        override fun toString(): String = "123.456"
    }

    @Test
    fun getBigDecimalOrZero() {
        assertEquals(BigDecimal("123"), BigDecimalUtils.getBigDecimalOrZero("123"))
        assertEquals(BigDecimal("123.456"), BigDecimalUtils.getBigDecimalOrZero("123.456"))
        assertEquals(BigDecimal("-123"), BigDecimalUtils.getBigDecimalOrZero("-123"))
        assertEquals(BigDecimal("-123.456"), BigDecimalUtils.getBigDecimalOrZero("-123.456"))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.getBigDecimalOrZero("NaN"))
        assertEquals(BigDecimal("123"), BigDecimalUtils.getBigDecimalOrZero(123))
        assertEquals(BigDecimal("123.456"), BigDecimalUtils.getBigDecimalOrZero(123.456))
        assertEquals(BigDecimal("123.456"), BigDecimalUtils.getBigDecimalOrZero(123.456f))
        assertEquals(BigDecimal("-123"), BigDecimalUtils.getBigDecimalOrZero(-123))
        assertEquals(BigDecimal("-123.456"), BigDecimalUtils.getBigDecimalOrZero(-123.456))
        assertEquals(BigDecimal("-123.456"), BigDecimalUtils.getBigDecimalOrZero(-123.456f))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.getBigDecimalOrZero(null))
        assertEquals(BigDecimal("123.456"), BigDecimalUtils.getBigDecimalOrZero(NumberObject))
    }

    @Test
    fun safeAdd() {
        assertEquals(BigDecimal("5"), BigDecimalUtils.safeAdd("3", "2"))
        assertEquals(BigDecimal("3"), BigDecimalUtils.safeAdd("3", null))
        assertEquals(BigDecimal("2"), BigDecimalUtils.safeAdd(null, "2"))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.safeAdd(null, null))
    }

    @Test
    fun safeSub() {
        assertEquals(BigDecimal("1"), BigDecimalUtils.safeSub("3", "2"))
        assertEquals(BigDecimal("3"), BigDecimalUtils.safeSub("3", null))
        assertEquals(BigDecimal("-2"), BigDecimalUtils.safeSub(null, "2"))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.safeSub(null, null))
    }

    @Test
    fun safeMul() {
        assertEquals(BigDecimal("6"), BigDecimalUtils.safeMul("3", "2"))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.safeMul("3", null))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.safeMul(null, "2"))
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.safeMul(null, null))
    }

    @Test
    fun safeDiv() {
        assertEquals(BigDecimal("1.50"), BigDecimalUtils.safeDiv("3", "2"))
        assertEquals(BigDecimal("0.00"), BigDecimalUtils.safeDiv("3", null))
        assertEquals(BigDecimal("0.00"), BigDecimalUtils.safeDiv(null, "2"))
        assertEquals(BigDecimal("0.00"), BigDecimalUtils.safeDiv(null, null))
        assertEquals(BigDecimal("0.00"), BigDecimalUtils.safeDiv("3", "0.00"))
    }

    @Test
    fun safeDivWithScale() {
        assertEquals(BigDecimal("1.5000"), BigDecimalUtils.safeDiv("3", "2", 4))
        assertEquals(BigDecimal("0.0000"), BigDecimalUtils.safeDiv("3", null, 4))
        assertEquals(BigDecimal("0.0000"), BigDecimalUtils.safeDiv(null, "2", 4))
        assertEquals(BigDecimal("0.0000"), BigDecimalUtils.safeDiv(null, null, 4))
        assertEquals(BigDecimal("0.0000"), BigDecimalUtils.safeDiv("3", "0.00", 4))
    }

    @Test
    fun safeDivWithScaleAndRoundingMode() {
        assertEquals(BigDecimal("1"), BigDecimalUtils.safeDiv(BigDecimal("3"), BigDecimal("2"), 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), BigDecimalUtils.safeDiv(BigDecimal("3"), null, 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), BigDecimalUtils.safeDiv(null, BigDecimal("2"), 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), BigDecimalUtils.safeDiv(null, null, 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), BigDecimalUtils.safeDiv("3", "0.00", 0, RoundingMode.DOWN))
    }

    @Test
    fun safeSum() {
        assertEquals(BigDecimal("6"), BigDecimalUtils.safeSum("1", "2", "3"))
        assertEquals(BigDecimal("3"), BigDecimalUtils.safeSum("1", "2", null))
        assertEquals(BigDecimal("0"), BigDecimalUtils.safeSum(null, null, null))
    }
}