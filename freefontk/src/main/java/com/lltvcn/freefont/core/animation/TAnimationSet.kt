package com.lltvcn.freefont.core.animation

import android.widget.TextView
import android.graphics.*
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/12/7.
 */
class TAnimationSet(tv: TextView) : BaseAnimation(tv) {
    private val animations = ArrayList<TAnimation?>()
    fun addTAnimation(animation: TAnimation?) {
        animations.add(animation)
    }

    override fun transformCanvas(index: Int, rect: RectF?, canvas: Canvas?, paint: Paint) {
        for (ani in animations) {
            ani!!.currentPlayTime = (animatedValue as Float * duration).toLong()
            ani.transformCanvas(index, rect, canvas, paint)
        }
    }
}