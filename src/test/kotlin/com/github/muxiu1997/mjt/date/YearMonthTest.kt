package com.github.muxiu1997.mjt.date

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.text.DateFormat
import java.time.LocalDate
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class YearMonthTest {
    @Test
    fun testConstructor() {
        assertThrows(IllegalArgumentException::class.java) { YearMonth("2022-13") }
        assertThrows(IllegalArgumentException::class.java) { YearMonth(2022, -1) }
    }

    @Test
    fun getYear() {
        assertEquals(YearMonth("2022-01").year, 2022)
    }

    @Test
    fun getMonth() {
        assertEquals(YearMonth("2022-01").month, 1)
    }

    @Test
    fun testToString() {
        assertEquals(YearMonth("2022-01").toString(), "2022-01")
    }

    @Test
    fun toDate() {
        assertEquals(YearMonth("2022-01").toDate(), DateFormat.getDateInstance().parse("2022-01-01"))
    }

    @Test
    fun toLocalDate() {
        assertEquals(YearMonth("2022-01").toLocalDate(), LocalDate.of(2022, 1, 1))
    }

    @Test
    fun prevMonth() {
        assertEquals(YearMonth("2022-01").prevMonth(), YearMonth("2021-12"))
        assertEquals(YearMonth("2022-02").prevMonth(), YearMonth("2022-01"))
    }

    @Test
    fun nextMonth() {
        assertEquals(YearMonth("2022-01").nextMonth(), YearMonth("2022-02"))
        assertEquals(YearMonth("2022-12").nextMonth(), YearMonth("2023-01"))
    }

    @Test
    fun prevYear() {
        assertEquals(YearMonth("2022-01").prevYear(), YearMonth("2021-01"))
    }

    @Test
    fun nextYear() {
        assertEquals(YearMonth("2022-01").nextYear(), YearMonth("2023-01"))
    }

    @Test
    fun rangeTo() {
        val range = YearMonth("2021-10")..YearMonth("2022-03")
        assertEquals(
            range.toList(), listOf(
                YearMonth("2021-10"),
                YearMonth("2021-11"),
                YearMonth("2021-12"),
                YearMonth("2022-01"),
                YearMonth("2022-02"),
                YearMonth("2022-03")
            )
        )
    }

    @Test
    fun compareTo() {
        assertTrue(YearMonth("2021-10") < YearMonth("2022-03"))
        assertTrue(YearMonth("2021-10") <= YearMonth("2022-03"))
        assertTrue(YearMonth("2021-10") <= YearMonth("2021-10"))
        assertTrue(YearMonth("2022-03") > YearMonth("2021-10"))
        assertTrue(YearMonth("2022-03") >= YearMonth("2021-10"))
        assertTrue(YearMonth("2021-10") >= YearMonth("2021-10"))
    }

    @Test
    fun testHashCode() {
        assertEquals(YearMonth("2021-10").hashCode(), YearMonth("2021-10").hashCode())
        assertNotEquals(YearMonth("2021-10").hashCode(), YearMonth("2021-11").hashCode())
    }

    @Test
    fun testEquals() {
        assertEquals(YearMonth("2021-10"), YearMonth("2021-10"))
        assertEquals(YearMonth("2021-10"), YearMonth(2021, 10))
        assertNotEquals(YearMonth("2021-10"), YearMonth("2021-11"))
        assertNotEquals<Any?>(YearMonth("2021-10"), null)
    }

    @Test
    fun fromLocalDate() {
        assertEquals(YearMonth.fromLocalDate(LocalDate.of(2021, 10, 1)), YearMonth("2021-10"))
    }

    @Test
    fun fromDate() {
        assertEquals(YearMonth.fromDate(DateFormat.getDateInstance().parse("2021-10-01")), YearMonth("2021-10"))
    }
}