package com.lltvcn.freefont.core.linedrawer

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by zhaolei on 2017/9/18.
 */
interface LineDrawer {
    fun draw(c: Canvas, p: Paint?, left: Float, top: Int, right: Float, bottom: Int, baseLine: Int)
}