package com.github.muxiu1997.mjt.validation


import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito
import javax.validation.ConstraintValidatorContext

internal class DateValidatorTest {

    companion object {
        val constraintValidatorContext: ConstraintValidatorContext =
            Mockito.mock(ConstraintValidatorContext::class.java)

        @Suppress("ArrayInDataClass")
        data class DateValidatorTestData(
            val patterns: Array<String>,
            val required: Boolean,
            val input: String?,
            val expected: Boolean,
        )

        @JvmStatic
        fun data(): Array<DateValidatorTestData> = arrayOf(
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = true,
                input = "2021-01-01",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = true,
                input = "2021-01-01 12:00:00",
                expected = false,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = true,
                input = null,
                expected = false,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = false,
                input = null,
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = false,
                input = "",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = false,
                input = "2021-01-01",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd"),
                required = false,
                input = "2021-01-01 12:00:00",
                expected = false,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = true,
                input = "2021-01-01",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = true,
                input = "2021-01-01 12:00:00",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = true,
                input = null,
                expected = false,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = false,
                input = null,
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = false,
                input = "",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = false,
                input = "2021-01-01",
                expected = true,
            ),
            DateValidatorTestData(
                patterns = arrayOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"),
                required = false,
                input = "2021-01-01 12:00:00",
                expected = true,
            ),
        )
    }

    @ParameterizedTest
    @MethodSource(value = ["data"])
    fun isValid(testData: DateValidatorTestData) {
        val validator = DateValidator()
        validator.initialize(Date(patterns = testData.patterns, required = testData.required))
        assertTrue(validator.isValid(testData.input, constraintValidatorContext) == testData.expected)
    }
}