package com.lltvcn.freefont.core.animation

interface TA<T> {
    fun start()
    fun stop()
    val duration: Long
    val value: T
}