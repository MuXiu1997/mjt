package com.github.muxiu1997.mjt.mybatisplus.extension

import com.baomidou.mybatisplus.extension.kotlin.AbstractKtWrapper
import kotlin.reflect.KProperty
import org.springframework.util.ReflectionUtils

inline fun <reified T, Children : AbstractKtWrapper<T, Children>> AbstractKtWrapper<T, Children>.columnStr(
    column: KProperty<*>,
): String =
    ReflectionUtils.findMethod(this::class.java, "columnToString", KProperty::class.java)!!
        .let {
            ReflectionUtils.makeAccessible(it)
            it.invoke(this, column) as String
        }

inline fun <reified T, Children : AbstractKtWrapper<T, Children>> AbstractKtWrapper<T, Children>.inOrEmpty(
    column: KProperty<*>,
    coll: Collection<*>?,
): AbstractKtWrapper<T, Children> = apply {
    if (coll.isNullOrEmpty()) {
        apply("1 = 0")
    } else {
        `in`(column, coll)
    }
}
