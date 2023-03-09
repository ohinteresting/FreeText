package com.lltvcn.freefont.core.layer

import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan
import android.graphics.*

/**
 * Created by zhaolei on 2017/10/21.
 */
class SingleWarpSpan(private val span: ReplacementSpan) : ReplacementSpan() {
    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        return span.getSize(paint, text, start, end, fm)
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        span.draw(canvas, text, start, end, x, top, y, bottom, paint)
    }
}