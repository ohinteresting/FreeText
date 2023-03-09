package com.lltvcn.freefont.core.layer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.lltvcn.freefont.core.animation.ICanvasTransform

/**
 * Created by zhaolei on 2017/9/21.
 */
interface ILayer {
    fun draw(index: Int, canvas: Canvas?, param: DrawParam?, paint: Paint)
    fun setRectF(rect: RectF, absSize: Float)
    fun offset(x: Float, y: Float)
    fun rotate(degree: Float)
    fun scale(scale: Float)
    fun setPaintHandler(shader: IPaintHandler?)
    fun setDrawDispatcher(dispatcher: IDrawDispatcher?)
    fun setCanvasTransform(transform: ICanvasTransform?)

    /**
     * layer 可以叠加,offset(x,y)，
     * txtlayer：mask(blur,emo---),textsize
     * imglayer:调整
     * 变换:分割，平移分割，波纹......
     * 以上中paint 通用的可以设置的属性：shader,mask,shadow, */
    interface IPaintHandler {
        fun handlePaint(index: Int, paint: Paint, rectF: RectF?)
    }

    interface IDrawDispatcher {
        fun draw(index: Int, layer: ILayer?, canvas: Canvas?, param: DrawParam?, paint: Paint)
    }

    interface DrawParam
}