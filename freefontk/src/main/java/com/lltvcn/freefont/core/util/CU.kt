package com.lltvcn.freefont.core.util

import android.graphics.drawable.Drawable
import android.graphics.*

/**
 * Created by zhaolei on 2017/10/21.
 */
object CU {
    fun toInt(color: String?): Int {
        return if (color!!.startsWith("#")) {
            Color.parseColor(color)
        } else {
            Color.parseColor("#$color")
        }
    }

    fun toInt(colors: Array<String?>?): IntArray {
        val result = IntArray(colors!!.size)
        for (i in result.indices) {
            result[i] = toInt(colors[i])
        }
        return result
    }

    @kotlin.jvm.JvmStatic
    fun toString(color: Int): String {
        return Integer.toHexString(color)
    }

    fun filterDrawable(drawable: Drawable?, color: Int) {
        if (drawable == null) return
        drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}