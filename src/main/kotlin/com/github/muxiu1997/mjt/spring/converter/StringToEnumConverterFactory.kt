package com.github.muxiu1997.mjt.spring.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.converter.ConverterFactory
import java.util.concurrent.ConcurrentHashMap

/**
 * Convert [String] to [Enum]
 *
 * Use the field or method annotated with the specific [annotationClass] on the [Enum] class to get the key,
 * if there is none of them, use the [Enum.name] as the key.
 *
 * If the key is duplicated, will throw [IllegalArgumentException]
 */
class StringToEnumConverterFactory(private val annotationClass: Class<out Annotation>) :
    ConverterFactory<String, Enum<*>> {
    private val converterMap: MutableMap<Class<*>, Converter<String, out Enum<*>>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Enum<*>> getConverter(targetType: Class<T>): Converter<String, T> {
        return converterMap.computeIfAbsent(targetType) { StringToEnumConverter(targetType) } as Converter<String, T>
    }

    internal inner class StringToEnumConverter<T : Enum<*>>(private val enumType: Class<T>) : Converter<String, T> {
        private val enumMap: MutableMap<String, T> = ConcurrentHashMap()

        init {
            val toKeyFunction = getToKeyFunction(enumType)
            val enums = enumType.enumConstants
            enums.forEach { e ->
                val k = toKeyFunction.invoke(e).toString()
                require(!enumMap.containsKey(k)) { "Duplicate key [$k] for enum ${enumType.name}" }
                enumMap[k] = e
            }
        }

        override fun convert(source: String): T =
            enumMap[source] ?: throw IllegalArgumentException("No element matches [$source] in enum ${enumType.name}")

        private fun getToKeyFunction(enumType: Class<T>): (T.() -> Any) {
            enumType.declaredFields
                .asSequence()
                .filter { it.isAnnotationPresent(annotationClass) }
                .forEach { field ->
                    return {
                        try {
                            field.isAccessible = true
                            field.get(this)
                        } catch (ignored: IllegalAccessException) {
                            throw IllegalArgumentException("Cannot access field [${field.name}] in enum ${enumType.name}")
                        }
                    }
                }
            enumType.declaredMethods
                .asSequence()
                .filter { it.parameterCount == 0 }
                .filter { it.returnType != Void.TYPE }
                .filter { it.isAnnotationPresent(annotationClass) }
                .forEach { method ->
                    return {
                        try {
                            method.isAccessible = true
                            method.invoke(this)
                        } catch (ignored: Exception) {
                            throw IllegalArgumentException("Cannot invoke method [${method.name}] in enum ${enumType.name}")
                        }
                    }
                }
            return { name }
        }
    }
}
