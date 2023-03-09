package com.lltvcn.freefont.core.animation

import android.widget.TextView
import android.animation.ValueAnimator
import android.graphics.*
import android.util.Log

/**
 * Created by zhaolei on 2017/12/4.
 */
class TAnimation(tv: TextView) : BaseAnimation(tv) {
    private var itemDuration: Long = 400
    private var fromX = 0f
    private var toX = 0f
    private var fromY = 0f
    private var toY = 0f
    private var fromAlpha = 0f
    private var toAlpha = 0f
    private var fromScaleX = 0f
    private var toScaleX = 0f
    private var fromScaleY = 0f
    private var toScaleY = 0f
    private var fromDegree = 0f
    private var toDegree = 0f
    private var hasAlpha = false
    private var hasTranslate = false
    private var hasRotate = false
    private var hasScale = false
    private var valueComputer: ValueComputer? = null
    private val matrix = Matrix()
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
    }

    override fun reverse() {
        checkDuration()
        super.reverse()
    }

    fun setItemDuration(duration: Long) {
        itemDuration = duration
    }

    private fun checkDuration() {
        val duration = valueComputer!!.getDuration(tv.text.length, this)
        if (duration != super.getDuration()) {
            super.setDuration(duration)
        }
    }

    class Builder(tv: TextView) {
        var a: TAnimation
        fun itemDuration(duration: Long): Builder {
            a.itemDuration = duration
            return this
        }

        fun alpha(from: Float, to: Float): Builder {
            a.hasAlpha = true
            a.fromAlpha = from
            a.toAlpha = to
            return this
        }

        fun translate(fromX: Float, toX: Float, fromY: Float, toY: Float): Builder {
            a.hasTranslate = true
            a.fromX = fromX
            a.toX = toX
            a.fromY = fromY
            a.toY = toY
            return this
        }

        fun scale(fromX: Float, toX: Float, fromY: Float, toY: Float): Builder {
            a.fromScaleX = fromX
            a.toScaleX = toX
            a.fromScaleY = fromY
            a.toScaleY = toY
            a.hasScale = true
            return this
        }

        fun rotate(from: Float, to: Float): Builder {
            a.hasRotate = true
            a.fromDegree = from
            a.toDegree = to
            return this
        }

        fun valueComputer(computer: ValueComputer?): Builder {
            a.valueComputer = computer
            return this
        }

        fun create(): TAnimation {
            return a
        }

        init {
            a = TAnimation(tv)
        }
    }

    interface ValueComputer {
        fun getVlaue(index: Int, animation: TAnimation): Float
        fun getDuration(count: Int, animation: TAnimation): Long
    }

    override fun transformCanvas(index: Int, rect: RectF?, canvas: Canvas?, paint: Paint) {
        if (valueComputer != null) {
            val value = valueComputer!!.getVlaue(index, this)
            if (valueComputer is SquenceComputer && (value == 0f || value == 1f)) {
                if ((255 * (fromAlpha + value * (toAlpha - fromAlpha))).toInt() == 255) {
                    Log.i(
                        "jisisos",
                        "value=" + value + "index------" + index + "-----" + currentPlayTime + "::::"
                    )
                }
            }
            if (hasAlpha) {
                paint.alpha = (255 * (fromAlpha + value * (toAlpha - fromAlpha))).toInt()
            }
            matrix.reset()
            var needTransform = false
            if (hasRotate) {
                needTransform = true
                matrix.setRotate(
                    fromDegree + value * (toDegree - fromDegree),
                    rect!!.centerX(),
                    rect.centerY()
                )
            }
            if (hasScale) {
                needTransform = true
                matrix.postScale(
                    fromScaleX + value * (toScaleX - fromScaleX),
                    fromScaleY + value * (toScaleY - fromScaleY),
                    rect!!.centerX(),
                    rect.centerY()
                )
            }
            if (hasTranslate) {
                needTransform = true
                matrix.postTranslate(
                    rect!!.width() * (fromX + value * (toX - fromX)),
                    rect.height() * (fromY + value * (toY - fromY))
                )
            }
            if (needTransform) {
                canvas!!.concat(matrix)
            }
        }
    }

    class SquenceComputer(private val offsetDuration: Long) : ValueComputer {
        override fun getVlaue(index: Int, animation: TAnimation): Float {
            val duration = (animation.currentPlayTime - offsetDuration * index).toFloat()
            return if (animation.isRunning) {
                if (duration > 0 && duration < animation.itemDuration) {
                    duration / animation.itemDuration
                } else if (duration <= 0) {
                    0f
                } else {
                    1f
                }
            } else {
                1f
            }
        }

        override fun getDuration(count: Int, animation: TAnimation): Long {
            return offsetDuration * (count - 1) + animation.itemDuration
        }
    }

    companion object {
        var SAME: ValueComputer = object : ValueComputer {
            override fun getVlaue(index: Int, animation: TAnimation): Float {
                return animation.animatedValue as Float
            }

            override fun getDuration(count: Int, animation: TAnimation): Long {
                return animation.itemDuration
            }
        }
        var NEG: ValueComputer = object : ValueComputer {
            override fun getVlaue(index: Int, animation: TAnimation): Float {
                return if (index % 2 == 0) {
                    animation.animatedValue as Float
                } else {
                    -(animation.animatedValue as Float)
                }
            }

            override fun getDuration(count: Int, animation: TAnimation): Long {
                return animation.itemDuration
            }
        }
        var REVERSE: ValueComputer = object : ValueComputer {
            override fun getVlaue(index: Int, animation: TAnimation): Float {
                return if (index % 2 == 0) {
                    animation.animatedValue as Float
                } else {
                    1 - animation.animatedValue as Float
                }
            }

            override fun getDuration(count: Int, animation: TAnimation): Long {
                return animation.itemDuration
            }
        }
    }
}