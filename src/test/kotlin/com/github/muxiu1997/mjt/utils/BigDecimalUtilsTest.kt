package com.github.muxiu1997.mjt.utils

import com.github.muxiu1997.mjt.utils.BigDecimalUtils.safeAdd
import com.github.muxiu1997.mjt.utils.BigDecimalUtils.safeDiv
import com.github.muxiu1997.mjt.utils.BigDecimalUtils.safeMul
import com.github.muxiu1997.mjt.utils.BigDecimalUtils.safeSub
import com.github.muxiu1997.mjt.utils.BigDecimalUtils.safeSum
import com.github.muxiu1997.mjt.utils.BigDecimalUtils.safeSumOf
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
        assertEquals(BigDecimal("5"), "3" safeAdd "2")
        assertEquals(BigDecimal("3"), "3" safeAdd null)
        assertEquals(BigDecimal("2"), null safeAdd "2")
        assertEquals(BigDecimal.ZERO, null safeAdd null)
    }

    @Test
    fun safeSub() {
        assertEquals(BigDecimal("1"), "3" safeSub "2")
        assertEquals(BigDecimal("3"), "3" safeSub null)
        assertEquals(BigDecimal("-2"), null safeSub "2")
        assertEquals(BigDecimal.ZERO, null safeSub null)
    }

    @Test
    fun safeMul() {
        assertEquals(BigDecimal("6"), "3" safeMul "2")
        assertEquals(BigDecimal.ZERO, "3" safeMul null)
        assertEquals(BigDecimal.ZERO, null safeMul "2")
        assertEquals(BigDecimal.ZERO, null safeMul null)
    }

    @Test
    fun safeDiv() {
        assertEquals(BigDecimal("1.50"), "3" safeDiv "2")
        assertEquals(BigDecimal("0.00"), "3" safeDiv null)
        assertEquals(BigDecimal("0.00"), null safeDiv "2")
        assertEquals(BigDecimal("0.00"), null safeDiv null)
        assertEquals(BigDecimal("0.00"), "3" safeDiv "0.00")
    }

    @Test
    fun safeDivWithScale() {
        assertEquals(BigDecimal("1.5000"), safeDiv("3", "2", 4))
        assertEquals(BigDecimal("0.0000"), safeDiv("3", null, 4))
        assertEquals(BigDecimal("0.0000"), safeDiv(null, "2", 4))
        assertEquals(BigDecimal("0.0000"), safeDiv(null, null, 4))
        assertEquals(BigDecimal("0.0000"), safeDiv("3", "0.00", 4))
    }

    @Test
    fun safeDivWithScaleAndRoundingMode() {
        assertEquals(BigDecimal("1"), safeDiv("3", "2", 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), safeDiv("3", null, 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), safeDiv(null, "2", 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), safeDiv(null, null, 0, RoundingMode.DOWN))
        assertEquals(BigDecimal("0"), safeDiv("3", "0.00", 0, RoundingMode.DOWN))
    }

    @Test
    fun safeSum_Array() {
        assertEquals(BigDecimal("6"), safeSum("1", "2", "3"))
        assertEquals(BigDecimal("3"), safeSum("1", "2", null))
        assertEquals(BigDecimal("0"), safeSum(null, null, null))
    }

    @Test
    fun safeSum_Iterable() {
        assertEquals(BigDecimal("6"), listOf("1", "2", "3").safeSum())
        assertEquals(BigDecimal("3"), listOf("1", "2", null).safeSum())
        assertEquals(BigDecimal("0"), listOf(null, null, null).safeSum())
    }

    @Test
    fun safeSum_Sequence() {
        assertEquals(BigDecimal("6"), listOf("1", "2", "3").asSequence().safeSum())
        assertEquals(BigDecimal("3"), listOf("1", "2", null).asSequence().safeSum())
        assertEquals(BigDecimal("0"), listOf(null, null, null).asSequence().safeSum())
    }


    @Test
    fun safeSumOf_Iterable() {
        assertEquals(BigDecimal("6"), listOf("1", "2", "3").safeSumOf { it })
        assertEquals(BigDecimal("3"), listOf("1", "2", null).safeSumOf { it })
        assertEquals(BigDecimal("0"), listOf(null, null, null).safeSumOf { it })
    }

    @Test
    fun safeSumOf_Sequence() {
        assertEquals(BigDecimal("6"), listOf("1", "2", "3").asSequence().safeSumOf { it })
        assertEquals(BigDecimal("3"), listOf("1", "2", null).asSequence().safeSumOf { it })
        assertEquals(BigDecimal("0"), listOf(null, null, null).asSequence().safeSumOf { it })
    }
}
