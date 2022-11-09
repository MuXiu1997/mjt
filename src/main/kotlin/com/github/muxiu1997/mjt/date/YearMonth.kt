package com.github.muxiu1997.mjt.date

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

class YearMonth(valueStr: String) {
    constructor(year: Int, month: Int) : this("$year-${month.toString().padStart(2, '0')}")

    private val value: LocalDate

    val year: Int get() = value.year
    val month: Int get() = value.monthValue

    init {
        try {
            value = LocalDate.parse(valueStr, PARSE_FORMATTER)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid year-month value: $valueStr")
        }
    }

    fun toDate(): Date = Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant())

    fun toLocalDate(): LocalDate = value

    fun prevMonth(): YearMonth = fromLocalDate(this.value.minusMonths(1))

    fun nextMonth(): YearMonth = fromLocalDate(this.value.plusMonths(1))

    fun prevYear(): YearMonth = fromLocalDate(this.value.minusYears(1))

    fun nextYear(): YearMonth = fromLocalDate(this.value.plusYears(1))

    operator fun rangeTo(other: YearMonth): Iterable<YearMonth> {
        return object : Iterable<YearMonth> {
            override fun iterator(): Iterator<YearMonth> {
                return object : Iterator<YearMonth> {
                    var current = this@YearMonth
                    override fun hasNext(): Boolean {
                        return current <= other
                    }

                    override fun next(): YearMonth {
                        val result = current
                        current = current.nextMonth()
                        return result
                    }
                }
            }
        }
    }

    override fun toString(): String = value.format(TO_STRING_FORMATTER)

    operator fun compareTo(other: YearMonth): Int = this.value.compareTo(other.value)

    override operator fun equals(other: Any?): Boolean = when (other is YearMonth) {
        true -> this.value == other.value
        false -> false
    }

    override fun hashCode(): Int = value.hashCode()

    companion object {
        val PARSE_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM")
            .parseDefaulting(java.time.temporal.ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
        val TO_STRING_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

        @JvmStatic
        fun fromLocalDate(date: LocalDate): YearMonth = YearMonth(date.format(PARSE_FORMATTER))

        @JvmStatic
        fun fromDate(date: Date): YearMonth =
            fromLocalDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
    }
}