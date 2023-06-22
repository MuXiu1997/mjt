package com.github.muxiu1997.mjt.mybatisplus.extension

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage

// region select
inline fun <reified T : Any> BaseMapper<T>.selectOneQW(block: QWB<T>): T? =
    this.selectOne(queryWrapper(block))

inline fun <reified T : Any> BaseMapper<T>.existsQW(block: QWB<T>): Boolean =
    this.exists(queryWrapper(block))

inline fun <reified T : Any> BaseMapper<T>.selectCountQW(block: QWB<T>): Long =
    this.selectCount(queryWrapper(block))

inline fun <reified T : Any> BaseMapper<T>.selectListQW(block: QWB<T>): MutableList<T> =
    this.selectList(queryWrapper(block))

inline fun <reified T : Any> BaseMapper<T>.selectMapsQW(block: QWB<T>): MutableList<Map<String, Any>> =
    this.selectMaps(queryWrapper(block))

inline fun <reified T : Any> BaseMapper<T>.selectObjsQW(block: QWB<T>): MutableList<Any> =
    this.selectObjs(queryWrapper(block))

inline fun <reified T : Any, P : IPage<T>> BaseMapper<T>.selectPageQW(page: P, block: QWB<T>): P =
    this.selectPage(page, queryWrapper(block))

inline fun <reified T : Any, P : IPage<Map<String, Any>>> BaseMapper<T>.selectMapsPageQW(page: P, block: QWB<T>): P =
    this.selectMapsPage(page, queryWrapper(block))
// endregion select

// region update
inline fun <reified T : Any> BaseMapper<T>.updateUW(entity: T?, block: UWB<T>): Int =
    this.update(entity, updateWrapper(block))

inline fun <reified T : Any> BaseMapper<T>.updateUW(block: UWB<T>): Int =
    this.update(null, updateWrapper(block))
// endregion update

// region delete
inline fun <reified T : Any> BaseMapper<T>.deleteQW(block: QWB<T>): Int =
    this.delete(queryWrapper(block))
// endregion delete
