package com.github.muxiu1997.mjt.utils

import com.github.muxiu1997.mjt.utils.YearMonthUtils.nextMonth
import com.github.muxiu1997.mjt.utils.YearMonthUtils.nextYear
import com.github.muxiu1997.mjt.utils.YearMonthUtils.prevMonth
import com.github.muxiu1997.mjt.utils.YearMonthUtils.prevYear
import com.github.muxiu1997.mjt.utils.YearMonthUtils.rangeTo
import com.github.muxiu1997.mjt.utils.YearMonthUtils.toDate
import com.github.muxiu1997.mjt.utils.YearMonthUtils.toLocalDate
import com.github.muxiu1997.mjt.utils.YearMonthUtils.toLocalDateTime
import com.github.muxiu1997.mjt.utils.YearMonthUtils.toYear
import com.github.muxiu1997.mjt.utils.YearMonthUtils.withMonth
import com.github.muxiu1997.mjt.utils.YearMonthUtils.withYear
import org.apache.commons.lang3.time.DateUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.*

internal class YearMonthUtilsTest {

    @Test
    fun fromDate() {
        assertEquals(YearMonth.of(2022, 1), YearMonthUtils.fromDate(DateUtils.parseDate("2022-01", "yyyy-MM")))
    }

    @Test
    fun fromLocalDate() {
        assertEquals(YearMonth.of(2022, 1), YearMonthUtils.fromLocalDate(LocalDate.of(2022, 1, 1)))
    }

    @Test
    fun fromLocalDateTime() {
        assertEquals(YearMonth.of(2022, 1), YearMonthUtils.fromLocalDateTime(LocalDate.of(2022, 1, 1).atStartOfDay()))
    }

    @Test
    fun toYear() {
        assertEquals(Year.of(2022), YearMonth.of(2022, 1).toYear())
    }

    @Test
    fun toDate() {
        assertEquals(DateUtils.parseDate("2022-01", "yyyy-MM"), YearMonth.of(2022, 1).toDate())
        assertEquals(DateUtils.parseDate("2022-01-02", "yyyy-MM-dd"), YearMonth.of(2022, 1).toDate(2))
    }

    @Test
    fun toLocalDate() {
        assertEquals(LocalDate.of(2022, 1, 1), YearMonth.of(2022, 1).toLocalDate())
        assertEquals(LocalDate.of(2022, 1, 2), YearMonth.of(2022, 1).toLocalDate(2))
    }

    @Test
    fun toLocalDateTime() {
        assertEquals(LocalDate.of(2022, 1, 1).atStartOfDay(), YearMonth.of(2022, 1).toLocalDateTime())
        assertEquals(
            LocalDateTime.of(2022, 1, 2, 3, 4),
            YearMonth.of(2022, 1).toLocalDateTime(2, 3, 4, 0)
        )
    }

    @Test
    fun prevMonth() {
        assertEquals(YearMonth.of(2021, 12), YearMonth.of(2022, 1).prevMonth())
        assertEquals(YearMonth.of(2022, 1), YearMonth.of(2022, 2).prevMonth())
    }

    @Test
    fun nextMonth() {
        assertEquals(YearMonth.of(2022, 2), YearMonth.of(2022, 1).nextMonth())
        assertEquals(YearMonth.of(2023, 1), YearMonth.of(2022, 12).nextMonth())
    }

    @Test
    fun prevYear() {
        assertEquals(YearMonth.of(2021, 1), YearMonth.of(2022, 1).prevYear())
    }

    @Test
    fun nextYear() {
        assertEquals(YearMonth.of(2023, 1), YearMonth.of(2022, 1).nextYear())
    }

    @Test
    fun withYear() {
        assertEquals(YearMonth.of(2023, 1), YearMonth.of(2022, 1).withYear(Year.of(2023)))
    }

    @Test
    fun withMonth() {
        assertEquals(YearMonth.of(2022, 2), YearMonth.of(2022, 1).withMonth(Month.FEBRUARY))
    }

    @Test
    fun rangeTo() {
        assertEquals(
            listOf(
                YearMonth.of(2022, 1),
                YearMonth.of(2022, 2),
                YearMonth.of(2022, 3),
                YearMonth.of(2022, 4),
                YearMonth.of(2022, 5),
                YearMonth.of(2022, 6),
                YearMonth.of(2022, 7),
                YearMonth.of(2022, 8),
                YearMonth.of(2022, 9),
                YearMonth.of(2022, 10),
                YearMonth.of(2022, 11),
                YearMonth.of(2022, 12),
            ),
            (YearMonth.of(2022, 1)..YearMonth.of(2022, 12)).toList()
        )
    }
}