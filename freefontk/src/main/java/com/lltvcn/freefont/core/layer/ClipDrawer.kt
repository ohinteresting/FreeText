package com.lltvcn.freefont.core.layer

import android.graphics.*

/**
 * Created by zhaolei on 2017/10/23.
 */
class ClipDrawer(private val size: Float) : DrawDiapatcher() {
    override fun drawToCanvas(
        bm: Bitmap?,
        c: Canvas?,
        p: Paint,
        aim: RectF?,
        from: Rect,
        to: RectF
    ) {
        val span = p.textSize * size
        c!!.save()
        from[aim!!.left.toInt(), aim.top.toInt(), aim.right.toInt()] =
            (aim.top + aim.height() / 2).toInt()
        //        from.set(0,0, (int) aim.width(),(int) (aim.height()/2));
        to.set(from)
        to.offset(0f, -span / 2)
        c.drawBitmap(bm!!, from, to, null)
        from.offset(0, (aim.height() / 2).toInt())
        to.offset(0f, aim.height() / 2 + span)
        c.drawBitmap(bm, from, to, null)
        c.restore()
    }
}