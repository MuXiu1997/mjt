package com.github.muxiu1997.mjt.validation

import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils
import java.text.ParseException
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DateValidator : ConstraintValidator<Date, String?> {
    private lateinit var patterns: Array<String>
    private var required: Boolean = false

    override fun initialize(constraintAnnotation: Date) {
        patterns = constraintAnnotation.patterns
        required = constraintAnnotation.required
    }

    override fun isValid(dateStr: String?, context: ConstraintValidatorContext): Boolean {
        if (dateStr.isNullOrEmpty()) return !required
        return patterns.any {
            try {
                DateFormatUtils.format(DateUtils.parseDate(dateStr, it), it) == dateStr
            } catch (ignored: ParseException) {
                false
            }
        }
    }
}
