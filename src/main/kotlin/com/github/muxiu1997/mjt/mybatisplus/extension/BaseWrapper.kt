package com.github.muxiu1997.mjt.mybatisplus.extension

import com.baomidou.mybatisplus.core.conditions.SharedString
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import java.lang.reflect.Constructor
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import org.springframework.util.ReflectionUtils

private val queryWrapperConstructorMap = ConcurrentHashMap<Class<*>, Constructor<QueryWrapper<*>>>()
private val updateWrapperConstructorMap = ConcurrentHashMap<Class<*>, Constructor<UpdateWrapper<*>>>()

private fun Any.getField(name: String): Any? {
    val field = ReflectionUtils.findField(this::class.java, name)!!
    ReflectionUtils.makeAccessible(field)
    return ReflectionUtils.getField(field, this)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> KtQueryWrapper<T>.baseWrapper(): QueryWrapper<T> {
    val constructor = queryWrapperConstructorMap.computeIfAbsent(this.entityClass) {
        ReflectionUtils.accessibleConstructor(
            QueryWrapper::class.java,
            Any::class.java,
            Class::class.java,
            AtomicInteger::class.java,
            Map::class.java,
            MergeSegments::class.java,
            SharedString::class.java,
            SharedString::class.java,
            SharedString::class.java,
            SharedString::class.java
        )
    }
    return constructor.newInstance(
        this.getField("entity"),
        this.getField("entityClass"),
        this.getField("paramNameSeq"),
        this.getField("paramNameValuePairs"),
        this.getField("expression"),
        this.getField("paramAlias"),
        this.getField("lastSql"),
        this.getField("sqlComment"),
        this.getField("sqlFirst")
    ) as QueryWrapper<T>
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> KtUpdateWrapper<T>.baseWrapper(): UpdateWrapper<T> {
    val constructor = updateWrapperConstructorMap.computeIfAbsent(this.entityClass) {
        ReflectionUtils.accessibleConstructor(
            UpdateWrapper::class.java,
            Any::class.java,
            List::class.java,
            AtomicInteger::class.java,
            Map::class.java,
            MergeSegments::class.java,
            SharedString::class.java,
            SharedString::class.java,
            SharedString::class.java,
            SharedString::class.java
        )
    }
    return constructor.newInstance(
        this.getField("entity"),
        this.getField("sqlSet"),
        this.getField("paramNameSeq"),
        this.getField("paramNameValuePairs"),
        this.getField("expression"),
        this.getField("paramAlias"),
        this.getField("lastSql"),
        this.getField("sqlComment"),
        this.getField("sqlFirst")
    ) as UpdateWrapper<T>
}
