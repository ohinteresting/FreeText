package com.lltvcn.freefont.core.animation

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * Created by zhaolei on 2017/12/4.
 */
interface ICanvasTransform {
    fun transformCanvas(index: Int, rect: RectF?, canvas: Canvas?, paint: Paint)
}