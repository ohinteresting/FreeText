package com.lltvcn.freefont.core.animation

import android.widget.TextView
import android.view.animation.*
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/12/6.
 */
class AnimationQueen(private val tv: TextView) : Animation() {
    private val animations = ArrayList<Animation>()
    private var currentAnimation: Animation? = null
    private var currentIndex = 0
//    private var repeatCount = 0
    fun addAnimation(animation: Animation): AnimationQueen {
        animations.add(animation)
        animation.setAnimationListener(listener)
        return this
    }

    override fun start() {
        if (currentAnimation != null) {
            cancel()
            tv.post { start() }
            return
        }
        currentAnimation = animations[0]
        currentIndex = 0
        repeatCount = 0
        if (currentAnimation != null) {
            tv.animation = currentAnimation
            currentAnimation!!.start()
        }
    }

    override fun cancel() {
        if (currentAnimation != null && currentAnimation!!.hasStarted()) {
            val animation: Animation = currentAnimation!!
            currentAnimation = null
            currentIndex = 0
            animation.cancel()
        }
        currentAnimation = null
        currentIndex = 0
        repeatCount = 0
    }

    override fun getDuration(): Long {
        var duration: Long = 0
        for (animation in animations) {
            duration += if (animation.repeatCount != INFINITE) {
                animation.repeatCount * animation.duration
            } else {
                return INFINITE.toLong()
            }
        }
        return duration
    }

    private val listener: AnimationListener = object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            if (currentAnimation != null) {
                var start = false
                if (currentIndex == animations.size - 1) {
                    repeatCount++
                    if (getRepeatCount() == INFINITE || repeatCount <= getRepeatCount()) {
                        currentIndex = 0
                        start = true
                    } else {
                        start = false
                        repeatCount = 0
                        currentIndex = 0
                        currentAnimation = null
                    }
                } else if (currentIndex < animations.size) {
                    start = true
                    currentIndex++
                }
                if (start) {
                    currentAnimation = animations[currentIndex]
                    tv.animation = currentAnimation
                    currentAnimation!!.start()
                }
            }
        }

        override fun onAnimationRepeat(animation: Animation) {}
    }
}