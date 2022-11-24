package com.github.muxiu1997.mjt.spring.converter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class StringToEnumConverterFactoryTest {
    @Test
    fun getConverter_Name() {
        val converter = StringToEnumConverterFactory(EnumValue::class.java).getConverter(TestEnumByName::class.java)
        assertEquals(TestEnumByName.A, converter.convert("A"))
        assertEquals(TestEnumByName.B, converter.convert("B"))
        assertEquals(TestEnumByName.C, converter.convert("C"))
        assertThrows<IllegalArgumentException> { converter.convert("unknown") }.run {
            assertEquals("No element matches [unknown] in enum ${TestEnumByName::class.java.name}", message)
        }
    }

    @Test
    fun getConverter_Field() {
        val converter =
            StringToEnumConverterFactory(EnumValue::class.java).getConverter(TestEnumByField::class.java)
        assertEquals(TestEnumByField.A, converter.convert("1"))
        assertEquals(TestEnumByField.B, converter.convert("2"))
        assertEquals(TestEnumByField.C, converter.convert("3"))
        assertThrows<IllegalArgumentException> { converter.convert("unknown") }.run {
            assertEquals("No element matches [unknown] in enum ${TestEnumByField::class.java.name}", message)
        }
    }

    @Test
    fun getConverter_Method() {
        val converter =
            StringToEnumConverterFactory(EnumValue::class.java).getConverter(TestEnumByMethod::class.java)
        assertEquals(TestEnumByMethod.A, converter.convert("1"))
        assertEquals(TestEnumByMethod.B, converter.convert("2"))
        assertEquals(TestEnumByMethod.C, converter.convert("3"))
        assertThrows<IllegalArgumentException> { converter.convert("unknown") }.run {
            assertEquals("No element matches [unknown] in enum ${TestEnumByMethod::class.java.name}", message)
        }
    }

    @Test
    fun getConverter_Duplicate() {
        assertThrows<IllegalArgumentException> {
            StringToEnumConverterFactory(EnumValue::class.java).getConverter(TestEnumByDuplicateField::class.java)
        }.run {
            assertEquals("Duplicate key [1] for enum ${TestEnumByDuplicateField::class.java.name}", message)
        }
    }


    companion object {
        @Target(
            AnnotationTarget.FIELD,
            AnnotationTarget.FUNCTION,
            AnnotationTarget.PROPERTY_GETTER,
            AnnotationTarget.PROPERTY_SETTER,
        )
        @Retention(AnnotationRetention.RUNTIME)
        private annotation class EnumValue

        private enum class TestEnumByName {
            A, B, C
        }

        @Suppress("unused")
        private enum class TestEnumByField(@EnumValue private val id: Int) {
            A(1), B(2), C(3)
        }

        @Suppress("unused")
        private enum class TestEnumByMethod(val id: Int) {
            A(1), B(2), C(3);

            @EnumValue
            private fun idStr(): String = id.toString()
        }

        @Suppress("unused")
        private enum class TestEnumByDuplicateField(@EnumValue private val id: Int) {
            A(1), B(1), C(1);
        }
    }
}
