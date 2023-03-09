package com.lltvcn.freefont.core.layer

interface SourceLoader<T> {
    fun loadByName(name: String): T
}