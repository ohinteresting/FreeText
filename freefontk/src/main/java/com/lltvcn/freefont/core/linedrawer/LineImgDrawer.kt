package com.lltvcn.freefont.core.linedrawer

import android.graphics.drawable.Drawable
import android.text.Spanned
import android.graphics.drawable.BitmapDrawable
import android.graphics.Paint.FontMetricsInt
import android.graphics.*
import android.text.style.LineHeightSpan.WithDensity
import android.text.TextPaint
import android.util.Log

/**
 * Created by zhaolei on 2017/9/20.
 */
open class LineImgDrawer(
    private val drawable: Drawable,
    private val relativeDrawableHeight: Float,
    private val gravity: Gravity
) : WithDensity, LineDrawer {
    private var aimHeight = 0
    private var aimOffset = 0
    private val fontMetricsInt = FontMetricsInt()
    private var startPos = -1
    private var endPos = 0
    private val offset = -0.1f
    override fun chooseHeight(
        text: CharSequence,
        start: Int,
        end: Int,
        spanstartv: Int,
        v: Int,
        fm: FontMetricsInt,
        paint: TextPaint
    ) {
        if (startPos == -1) {
            startPos = (text as Spanned).getSpanStart(this)
            endPos = text.getSpanEnd(this)
        }
        Log.i("line", "start:" + startPos + "end:" + endPos + "ssss" + start + "eeee" + end)
        if (endPos < start || startPos >= end) {
            return
        }
        updateFM(fm, paint.textSize.toInt())
    }

    private fun updateFM(fm: FontMetricsInt, txtHeight: Int) {
        Log.i("line----start", fm.toString())
        aimHeight = (relativeDrawableHeight * txtHeight).toInt()
        aimOffset = (relativeDrawableHeight * txtHeight).toInt()
        when (gravity) {
            Gravity.CENTER -> if (aimHeight > txtHeight) {
                val plus = (aimHeight - txtHeight) / 2
                fm.top -= plus
                fm.ascent -= plus
                fm.bottom += plus
                fm.descent += plus
            }
            Gravity.OUT_TOP -> {
                fm.top -= aimHeight
                fm.ascent -= aimHeight
            }
            Gravity.OUT_BOTTOM -> {
                fm.bottom += aimHeight
                fm.descent += aimHeight
            }
            Gravity.INNER_TOP -> if (aimHeight > txtHeight) {
                val plus = aimHeight - txtHeight
                fm.bottom += plus
                fm.descent += plus
            }
            Gravity.INNER_BOTTOM -> if (aimHeight > txtHeight) {
                val plus = aimHeight - txtHeight
                fm.top -= plus
                fm.ascent -= plus
            }
        }
        fontMetricsInt.top = fm.top
        fontMetricsInt.ascent = fm.ascent
        fontMetricsInt.bottom = fm.bottom
        fontMetricsInt.descent = fm.descent
        Log.i("line---end", fm.toString())
    }

    override fun chooseHeight(
        text: CharSequence,
        start: Int,
        end: Int,
        spanstartv: Int,
        v: Int,
        fm: FontMetricsInt
    ) {
    }

    override fun draw(
        c: Canvas,
        p: Paint?,
        left: Float,
        top: Int,
        right: Float,
        bottom: Int,
        baseLine: Int
    ) {
        var top = top
        var bottom = bottom
        Log.i("line-draw", "top:" + top + "bottom:" + bottom)
        top = baseLine + fontMetricsInt.ascent
        bottom = baseLine + fontMetricsInt.descent
        when (gravity) {
            Gravity.CENTER -> {
                val moreH = bottom - top - aimHeight
                top += moreH / 2
                bottom -= moreH / 2
            }
            Gravity.INNER_TOP, Gravity.OUT_TOP -> bottom = top + aimHeight
            Gravity.INNER_BOTTOM, Gravity.OUT_BOTTOM -> top = bottom - aimHeight
        }
        if (drawable is BitmapDrawable) {
            val dstHeight = (bottom - top).toFloat()
            val scale = dstHeight / drawable.getIntrinsicHeight()
            val width = (drawable.getIntrinsicWidth() * scale).toInt()
            var leftIndex = left.toInt()
            while (leftIndex < right) {
                if (leftIndex + width > right) {
                    c.save()
                    c.clipRect(leftIndex.toFloat(), top.toFloat(), right, bottom.toFloat())
                    drawable.setBounds(leftIndex, top, leftIndex + width, bottom)
                    drawable.draw(c)
                    c.restore()
                } else {
                    drawable.setBounds(leftIndex, top, leftIndex + width, bottom)
                    drawable.draw(c)
                }
                leftIndex += width
            }
        } else {
            drawable.setBounds(left.toInt(), top, right.toInt(), bottom)
            drawable.draw(c)
        }
    }
}