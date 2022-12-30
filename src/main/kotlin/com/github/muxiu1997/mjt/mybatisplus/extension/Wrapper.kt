package com.github.muxiu1997.mjt.mybatisplus.extension

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper

internal typealias QWB<T> = KtQueryWrapper<T>.() -> Unit
internal typealias UWB<T> = KtUpdateWrapper<T>.() -> Unit

inline fun <reified T : Any> queryWrapper(block: QWB<T>): KtQueryWrapper<T> =
    KtQueryWrapper(T::class.java).apply(block)

inline fun <reified T : Any> updateWrapper(block: UWB<T>): KtUpdateWrapper<T> =
    KtUpdateWrapper(T::class.java).apply(block)
