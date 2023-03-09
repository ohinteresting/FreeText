package com.lltvcn.freefont.core.layer

import android.graphics.*

/**
 * Created by zhaolei on 2017/10/23.
 */
class OffsetDrawer(private val positions: FloatArray?, private val offsets: FloatArray?) :
    DrawDiapatcher() {
    override fun drawToCanvas(
        bm: Bitmap?,
        c: Canvas?,
        p: Paint,
        aim: RectF?,
        from: Rect,
        to: RectF
    ) {
        if (positions != null) {
            var offsetY = 0
            for (i in positions.indices) {
                from[aim!!.left.toInt(), aim.top.toInt() + offsetY, aim.right.toInt()] =
                    (aim.top.toInt() + aim.height() * positions[i]
                        .let {
                            offsetY += it.toInt()
                            offsetY
                        }).toInt()
                if (offsetY > aim.bottom) {
                    return
                }
                to.set(from)
                to.offset(offsets!![i] * p.textSize, 0f)
                c!!.drawBitmap(bm!!, from, to, null)
            }
        }
    }
}