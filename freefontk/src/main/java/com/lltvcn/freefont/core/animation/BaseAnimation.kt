package com.lltvcn.freefont.core.animation

import android.widget.TextView
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener

/**
 * Created by zhaolei on 2017/12/4.
 */
abstract class BaseAnimation(protected var tv: TextView) : ValueAnimator(), ICanvasTransform,
    AnimatorUpdateListener {
    override fun onAnimationUpdate(animation: ValueAnimator) {
        tv.postInvalidate()
    }

    init {
        setFloatValues(0f, 1f)
        addUpdateListener(this)
    }
}