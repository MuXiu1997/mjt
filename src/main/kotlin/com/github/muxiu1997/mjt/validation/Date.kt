package com.github.muxiu1997.mjt.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * The annotated element must be a formatted date with specific patterns. Accepts [CharSequence].
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.TYPE
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DateValidator::class])
annotation class Date(
    /**
     * @return patterns of this date, default is `["yyyy-MM-dd'T'HH:mm:ss"]`
     */
    val patterns: Array<String> = ["yyyy-MM-dd'T'HH:mm:ss"],
    /**
     * @return this date is required or not, default is `true`
     */
    val required: Boolean = true,
    val message: String = "invalid date",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
