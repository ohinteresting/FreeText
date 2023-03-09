package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.animation.ICanvasTransform
import com.lltvcn.freefont.core.layer.ILayer.DrawParam
import com.lltvcn.freefont.core.layer.ILayer.IPaintHandler
import com.lltvcn.freefont.core.layer.ILayer.IDrawDispatcher
import android.graphics.*

/**
 * Created by zhaolei on 2017/9/26.
 */
abstract class BaseLayer : ILayer {
    private var paintHandler: IPaintHandler? = null
    private var drawDispatcher: IDrawDispatcher? = null
    private var canvasTransform: ICanvasTransform? = null
    protected var offsetX = 0f
    protected var offsetY = 0f
    protected var degree = 0f
    protected var scale = 0f
    private var absSize = 0f
    var rect: RectF? = null
    override fun setRectF(rect: RectF, absSize: Float) {
        this.absSize = absSize
        if (this.rect == null || this.rect !== rect) {
            this.rect = rect
            onRectChange(rect, absSize)
        }
    }

    protected open fun onRectChange(rect: RectF, absSize: Float) {}
    override fun offset(x: Float, y: Float) {
        offsetX = x
        offsetY = y
    }

    override fun rotate(degree: Float) {
        this.degree = degree
    }

    override fun scale(scale: Float) {
        this.scale = scale
    }

    override fun setCanvasTransform(canvasTransform: ICanvasTransform?) {
        this.canvasTransform = canvasTransform
    }

    override fun setPaintHandler(shader: IPaintHandler?) {
        paintHandler = shader
    }

    override fun draw(index: Int, canvas: Canvas?, param: DrawParam?, paint: Paint) {
        if (offsetX != 0f || offsetY != 0f || degree != 0f || scale != 0f && scale != 1f) {
            canvas!!.save()
            canvas.translate(offsetX * absSize, offsetY * absSize)
            if (degree != 0f) {
                canvas.rotate(degree, rect!!.centerX(), rect!!.centerY())
            }
            if (scale != 0f && scale != 1f) {
                canvas.scale(scale, scale, rect!!.centerX(), rect!!.centerY())
            }
            paint.reset()
            paint.set(paint)
            if (paintHandler != null) {
                paintHandler!!.handlePaint(index, paint, rect)
            }
            if (canvasTransform != null) {
                canvas.save()
                canvasTransform!!.transformCanvas(index, rect, canvas, paint)
            }
            if (drawDispatcher != null) {
                drawDispatcher!!.draw(index, this, canvas, param, paint)
            } else {
                drawLayer(index, canvas, param, paint)
            }
            if (canvasTransform != null) {
                canvas.restore()
            }
            canvas.restore()
        } else {
            paint.reset()
            paint.set(paint)
            if (paintHandler != null) {
                paintHandler!!.handlePaint(index, paint, rect)
            }
            if (canvasTransform != null) {
                canvas!!.save()
                canvasTransform!!.transformCanvas(index, rect, canvas, paint)
            }
            if (drawDispatcher != null) {
                drawDispatcher!!.draw(index, this, canvas, param, paint)
            } else {
                drawLayer(index, canvas, param, paint)
            }
            if (canvasTransform != null) {
                canvas!!.restore()
            }
        }
    }

    override fun setDrawDispatcher(dispatcher: IDrawDispatcher?) {
        drawDispatcher = dispatcher
    }

    open fun drawLayer(index: Int, c: Canvas?, param: DrawParam?, paint: Paint) {}

    companion object {
        private val paint = Paint()
    }
}