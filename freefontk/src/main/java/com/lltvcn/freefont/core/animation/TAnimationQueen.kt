package com.lltvcn.freefont.core.animation

import android.widget.TextView
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.*
import android.util.Log
import java.lang.Exception
import java.lang.reflect.Field
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/12/5.
 */
class TAnimationQueen @SuppressLint("SoonBlockedPrivateApi") constructor(tv: TextView) :
    BaseAnimation(tv) {
    private val animations = ArrayList<BaseAnimation?>()
    private var fRunning: Field? = null
    fun addAnimation(animation: BaseAnimation?) {
        if (!animations.contains(animation)) {
            animations.add(animation)
        }
    }

    @Deprecated("")
    override fun setDuration(duration: Long): ValueAnimator {
        return this
    }

    override fun getDuration(): Long {
        checkDuration()
        return super.getDuration()
    }

    override fun start() {
        checkDuration()
        super.start()
        if (fRunning == null) {
            Log.e("TAnimationQueen", "start fRunning is null")
            return
        }
        try {
            fRunning!!.isAccessible = true
            for (animation in animations) {
                fRunning!!.setBoolean(animation, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        fRunning!!.isAccessible = false
    }

    private fun checkDuration() {
        var duration: Long = 0
        for (animation in animations) {
            duration += animation!!.duration
        }
        if (duration != super.getDuration()) {
            super.setDuration(duration)
        }
    }

    override fun reverse() {
        checkDuration()
        super.reverse()
    }

    override fun end() {
        super.end()
        if (fRunning == null) {
            Log.e("TAnimationQueen", "end fRunning is null")
            return
        }
        for (animation in animations) {
            fRunning!!.isAccessible = true
            try {
                fRunning!!.setBoolean(animation, false)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            fRunning!!.setAccessible(false)
        }
    }

    override fun transformCanvas(index: Int, rect: RectF?, canvas: Canvas?, paint: Paint) {
        val currentTime = (animatedValue as Float * duration).toLong()
        var time: Long = 0
        var duration: Long
        var hasTrans = false
        for (animation in animations) {
            duration = animation!!.duration
            if (time <= currentTime && time + duration > currentTime) {
                hasTrans = true
                Log.i(
                    "kkais",
                    "currentTime:" + currentTime + "duration:" + duration + "::time" + time
                )
                animation.currentPlayTime = currentTime - time
                animation.transformCanvas(index, rect, canvas, paint)
                break
            }
            time += duration
        }
        if (!hasTrans) {
            Log.i("ddsiis", "no")
        }
    }

    init {
        try {
            fRunning = ValueAnimator::class.java.getDeclaredField("mRunning")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }
}