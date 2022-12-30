package com.github.muxiu1997.mjt.extension.kotlin

inline fun <reified T : CharSequence> T?.ifNotBlank(block: (T) -> Unit) {
    if (!this.isNullOrBlank()) block(this)
}
