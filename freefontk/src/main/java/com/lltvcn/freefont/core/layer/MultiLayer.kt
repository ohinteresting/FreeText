package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.layer.ILayer.DrawParam
import android.graphics.*
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/10/19.
 */
class MultiLayer : BaseLayer {
    protected var layers: ArrayList<ILayer?>? = null

    constructor(layers: ArrayList<ILayer?>?) {
        this.layers = layers
    }

    constructor() {}

    override fun onRectChange(rect: RectF, absSize: Float) {
        if (layers != null) {
            for (layer in layers!!) {
                layer!!.setRectF(rect, absSize)
            }
        }
    }

    fun add(layer: ILayer?) {
        if (layers == null) {
            layers = ArrayList(1)
        }
        layers!!.add(layer)
    }

    fun remove(layer: ILayer?) {
        layers!!.remove(layer)
    }

    override fun drawLayer(index: Int, c: Canvas?, param: DrawParam?, paint: Paint) {
        multiPaint.reset()
        multiPaint.set(paint)
        if (layers != null) {
            for (layer in layers!!) {
                layer!!.draw(index, c, param, multiPaint)
            }
        }
    }

    companion object {
        private val multiPaint = Paint()
    }
}