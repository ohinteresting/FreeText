package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.layer.ILayer.DrawParam
import com.lltvcn.freefont.core.layer.ILayer.IDrawDispatcher
import android.graphics.*

/**
 * Created by zhaolei on 2017/10/23.
 */
abstract class DrawDiapatcher : IDrawDispatcher {
    override fun draw(index: Int, layer: ILayer?, c: Canvas?, param: DrawParam?, p: Paint) {
        if (layer is BaseLayer) {
            val baseLayer = layer
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
                canvas = Canvas(bitmap!!)
            }
            paint.color = Color.TRANSPARENT
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            canvas!!.drawPaint(paint)
            baseLayer.drawLayer(index, canvas, param, p)
            drawToCanvas(bitmap, c, p, baseLayer.rect, fromRect, toRect)
        }
    }

    protected fun setRect(res: RectF, to: Rect) {
        to[res.left.toInt(), res.top.toInt(), res.right.toInt()] = res.bottom.toInt()
    }

    protected abstract fun drawToCanvas(
        bm: Bitmap?,
        c: Canvas?,
        p: Paint,
        aim: RectF?,
        from: Rect,
        to: RectF
    )

    companion object {
        private var bitmap: Bitmap? = null
        private var canvas: Canvas? = null
        private val paint = Paint()
        private val toRect = RectF()
        private val fromRect = Rect()
    }
}