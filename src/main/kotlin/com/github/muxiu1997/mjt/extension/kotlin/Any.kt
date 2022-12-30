package com.github.muxiu1997.mjt.extension.kotlin

inline fun <reified T : Any> T?.ifNotNull(block: (T) -> Unit) {
    if (this != null) block(this)
}
