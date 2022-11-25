package com.github.muxiu1997.mjt.validation

import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils
import java.text.ParseException
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * Check that a character sequence is a valid date.
 */
class DateValidator : ConstraintValidator<Date, CharSequence?> {
    private lateinit var patterns: Array<String>
    private var required: Boolean = false

    override fun initialize(constraintAnnotation: Date) {
        patterns = constraintAnnotation.patterns
        required = constraintAnnotation.required
    }

    /**
     * Check that a character sequence is a valid date.
     */
    override fun isValid(charSequence: CharSequence?, context: ConstraintValidatorContext): Boolean {
        if (charSequence.isNullOrEmpty()) return !required
        val dateStr = charSequence.toString()
        return patterns.any {
            try {
                DateFormatUtils.format(DateUtils.parseDate(dateStr, it), it) == dateStr
            } catch (ignored: ParseException) {
                false
            }
        }
    }
}
