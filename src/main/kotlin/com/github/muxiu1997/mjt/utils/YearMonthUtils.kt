package com.github.muxiu1997.mjt.utils

import com.github.muxiu1997.mjt.utils.YearMonthUtils.nextMonth
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils
import org.jetbrains.annotations.Contract
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object YearMonthUtils {
    /**
     * Returns the [YearMonth] converted from the specified [Date].
     */
    @JvmStatic
    @Contract(pure = true)
    fun fromDate(date: Date): YearMonth = YearMonth.parse(DateFormatUtils.format(date, "yyyy-MM"))

    /**
     * Returns the [YearMonth] converted from the specified [LocalDate].
     */
    @JvmStatic
    @Contract(pure = true)
    fun fromLocalDate(localDate: LocalDate): YearMonth = YearMonth.of(localDate.year, localDate.month)

    /**
     * Returns the [YearMonth] from the specified [LocalDateTime].
     */
    @JvmStatic
    @Contract(pure = true)
    fun fromLocalDateTime(localDateTime: LocalDateTime): YearMonth =
        YearMonth.of(localDateTime.year, localDateTime.month)

    /**
     * Returns the [Year] converted from this [YearMonth].
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.toYear(): Year = Year.of(this.year)

    /**
     * Returns the [Date] converted from this [YearMonth].
     * @param dayOfMonth the day of month, default is 1
     */
    @JvmStatic
    @JvmOverloads
    @Contract(pure = true)
    fun YearMonth.toDate(dayOfMonth: Int = 1): Date =
        DateUtils.parseDate(this.toLocalDate(dayOfMonth).format(DateTimeFormatter.ISO_LOCAL_DATE), "yyyy-MM-dd")

    /**
     * Returns the [LocalDate] converted from this [YearMonth].
     * @param dayOfMonth the day of month, default is 1
     */
    @JvmStatic
    @JvmOverloads
    @Contract(pure = true)
    fun YearMonth.toLocalDate(dayOfMonth: Int = 1): LocalDate = LocalDate.of(this.year, this.month, dayOfMonth)

    /**
     * Returns the [LocalDateTime] converted from this [YearMonth].
     * @param dayOfMonth the day of month, default is 1
     * @param hour the hour, default is 0
     * @param minute the minute, default is 0
     * @param second the second, default is 0
     */
    @JvmStatic
    @JvmOverloads
    @Contract(pure = true)
    fun YearMonth.toLocalDateTime(dayOfMonth: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): LocalDateTime =
        LocalDateTime.of(this.year, this.month, dayOfMonth, hour, minute, second)

    /**
     * Returns the previous month of this [YearMonth].
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.prevMonth(): YearMonth = this.minusMonths(1)

    /**
     * Returns the next month of this [YearMonth].
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.nextMonth(): YearMonth = this.plusMonths(1)

    /**
     * Returns the previous year of this [YearMonth].
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.prevYear(): YearMonth = this.minusYears(1)

    /**
     * Returns the next year of this [YearMonth].
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.nextYear(): YearMonth = this.plusYears(1)

    /**
     * Returns a copy of this [YearMonth] with the [Year] altered.
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.withYear(year: Year): YearMonth = YearMonth.of(year.value, this.monthValue)

    /**
     * Returns a copy of this [YearMonth] with the [Month] altered.
     */
    @JvmStatic
    @Contract(pure = true)
    fun YearMonth.withMonth(month: Month): YearMonth = YearMonth.of(this.year, month)

    /**
     * Creates a range from this [YearMonth] to the specified [YearMonth].
     */
    @JvmStatic
    @Contract(pure = true)
    operator fun YearMonth.rangeTo(other: YearMonth): YearMonthRange = YearMonthRange(this, other)
}

/**
 * Represents a range of [YearMonth] where both the lower and upper bounds are included in the range.
 */
class YearMonthRange(override val start: YearMonth, override val endInclusive: YearMonth) : Iterable<YearMonth>,
    ClosedRange<YearMonth> {
    override fun iterator(): Iterator<YearMonth> {
        return object : Iterator<YearMonth> {
            var current = start
            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): YearMonth {
                return current.apply { current = nextMonth() }
            }
        }
    }
}
