package com.lltvcn.freefont.core.animation


import android.widget.TextView
import android.animation.ValueAnimator
import com.lltvcn.freefont.core.animation.TAnimation.SquenceComputer
import android.view.animation.*

/**
 * Created by zhaolei on 2017/12/6.
 */
object A {
    const val SINGLE_RIGHT_FADE_INF_LEFT_FADE_OUT = 0
    const val SINGLE_ROTATE = 1
    const val SINGLE_UP_DOWN = 2
    const val ROTATE_REPEAT = 3
    const val SCALE_SHOW = 4
    const val BOTTOM_IN_SCALE_UP_OUT = 5
    const val SINGLE_SCALE = 6
    const val SINGLE_X = 7
    fun createTAByType(type: Int, tv: TextView): TA<*>? {
        when (type) {
            SINGLE_RIGHT_FADE_INF_LEFT_FADE_OUT -> return createSRILO(tv)
            SINGLE_ROTATE -> return createSRotate(tv)
            ROTATE_REPEAT -> return createRoteteRepeat(tv)
            BOTTOM_IN_SCALE_UP_OUT -> return createBISUO(tv)
            SINGLE_UP_DOWN -> return createSUD(tv)
            SCALE_SHOW -> return createScaleShow(tv)
            SINGLE_SCALE -> return createSS(tv)
            SINGLE_X -> return createX(tv)
        }
        return null
    }

    private fun createX(tv: TextView): TA<*> {
        val duration: Long = 200
        val queen = TAnimationQueen(tv)
        val set = TAnimationSet(tv)
        val rotateAni = TAnimation.Builder(tv)
            .rotate(0f, -7f)
            .itemDuration(duration)
            .valueComputer(TAnimation.Companion.NEG)
            .create()
        val trans = TAnimation.Builder(tv)
            .translate(0f, 0f, 0f, -0.1f)
            .valueComputer(TAnimation.Companion.SAME)
            .itemDuration(duration)
            .create()
        set.addTAnimation(rotateAni)
        set.addTAnimation(trans)
        set.duration = duration
        val set2 = TAnimationSet(tv)
        val rotateAni2 = TAnimation.Builder(tv)
            .rotate(-7f, 7f)
            .itemDuration(duration)
            .valueComputer(TAnimation.Companion.REVERSE)
            .create()
        val trans2 = TAnimation.Builder(tv)
            .translate(0f, 0f, -0.1f, 0f)
            .itemDuration(duration)
            .valueComputer(TAnimation.Companion.SAME)
            .create()
        set2.addTAnimation(rotateAni2)
        set2.addTAnimation(trans2)
        set2.duration = duration
        val set3 = TAnimationSet(tv)
        val rotateAni3 = TAnimation.Builder(tv)
            .rotate(7f, -7f)
            .itemDuration(duration)
            .valueComputer(TAnimation.Companion.REVERSE)
            .create()
        val trans3 = TAnimation.Builder(tv)
            .translate(0f, 0f, 0f, -0.1f)
            .itemDuration(duration)
            .valueComputer(TAnimation.Companion.SAME)
            .create()
        set3.addTAnimation(rotateAni3)
        set3.addTAnimation(trans3)
        set3.duration = duration
        val set4 = TAnimationSet(tv)
        val rotateAni4 = TAnimation.Builder(tv)
            .rotate(-7f, 7f)
            .itemDuration(duration * 2)
            .valueComputer(TAnimation.Companion.REVERSE)
            .create()
        val trans4 = TAnimation.Builder(tv)
            .translate(0f, 0f, -0.1f, 0f)
            .itemDuration(duration)
            .valueComputer(TAnimation.Companion.SAME)
            .create()
        set4.addTAnimation(rotateAni4)
        set4.addTAnimation(trans4)
        set4.duration = duration
        //
//
        queen.addAnimation(set)
        queen.addAnimation(set2)
        queen.addAnimation(set3)
        queen.addAnimation(set4)
        queen.repeatMode = ValueAnimator.RESTART
        queen.repeatCount = ValueAnimator.INFINITE
        //        queen.addAnimation(set3);
//        queen.addAnimation(set4);
        return BaseAnimation2IA(queen)
    }

    private fun createScaleShow(tv: TextView): TA<*> {
        val sa = ScaleAnimation(
            4f,
            1f,
            4f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        sa.startOffset = 500
        sa.duration = 600
        sa.repeatCount = Animation.INFINITE
        sa.repeatMode = Animation.RESTART
        tv.animation = sa
        return Animation2IA(sa)
    }

    private fun createBISUO(tv: TextView): TA<*> {
        val queen = AnimationQueen(tv)
        val upDuration: Long = 200
        val scaleDuration: Long = 200
        val upIn = AnimationSet(false)
        val alphaIn: Animation = AlphaAnimation(0f, 1f)
        alphaIn.duration = upDuration
        val translateIn: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            1f,
            Animation.RELATIVE_TO_SELF,
            0f
        )
        translateIn.duration = upDuration
        upIn.addAnimation(alphaIn)
        upIn.addAnimation(translateIn)
        val scaleRightLarge = AnimationSet(false)
        val scaleIn: Animation = ScaleAnimation(
            1f,
            1.5f,
            1f,
            1.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scaleIn.duration = scaleDuration
        val rotateLeftIn: Animation = RotateAnimation(
            0f,
            10f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateLeftIn.duration = scaleDuration
        rotateLeftIn.repeatCount = 1
        rotateLeftIn.repeatMode = Animation.REVERSE
        scaleIn.repeatCount = 1
        scaleIn.repeatMode = Animation.REVERSE
        scaleRightLarge.addAnimation(scaleIn)
        scaleRightLarge.addAnimation(rotateLeftIn)
        val scaleLeftLarge = AnimationSet(false)
        val rotateRightIn: Animation = RotateAnimation(
            0f,
            -10f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scaleLeftLarge.duration = scaleDuration
        rotateRightIn.repeatCount = 1
        rotateRightIn.repeatMode = Animation.REVERSE
        scaleLeftLarge.addAnimation(scaleIn)
        scaleLeftLarge.addAnimation(rotateRightIn)
        val upOut = AnimationSet(false)
        val alphaOut: Animation = AlphaAnimation(1f, 0f)
        alphaOut.duration = upDuration
        val translateOut: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -1f
        )
        translateOut.duration = upDuration
        upOut.addAnimation(alphaOut)
        upOut.addAnimation(translateOut)
        queen.addAnimation(upIn)
        queen.addAnimation(scaleRightLarge)
        queen.addAnimation(upOut)
        queen.addAnimation(upIn)
        queen.addAnimation(scaleLeftLarge)
        queen.addAnimation(upOut)
        queen.repeatMode = Animation.RESTART
        queen.repeatCount = Animation.INFINITE
        return Animation2IA(queen)
    }

    private fun createRoteteRepeat(tv: TextView): TA<*> {
        val animation = RotateAnimation(
            -3f,
            3f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 150
        //        animation.setInterpolator(new DecelerateInterpolator());
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        tv.animation = animation
        return Animation2IA(animation)
    }

    private fun createSRotate(tv: TextView): TA<*> {
        val rotateAni = TAnimation.Builder(tv)
            .rotate(-10f, 10f)
            .valueComputer(TAnimation.Companion.REVERSE)
            .itemDuration(200)
            .create()
        rotateAni!!.repeatMode = ValueAnimator.REVERSE
        rotateAni.repeatCount = ValueAnimator.INFINITE
        return BaseAnimation2IA(rotateAni)
    }

    private fun createSS(tv: TextView): TA<*> {
        val sa = TAnimation.Builder(tv)
            .scale(1.1f, 0.8f, 1.1f, 0.8f)
            .itemDuration(80)
            .valueComputer(TAnimation.Companion.REVERSE)
            .create()
        sa!!.repeatMode = ValueAnimator.REVERSE
        sa.repeatCount = ValueAnimator.INFINITE
        return BaseAnimation2IA(sa)
    }

    private fun createSUD(tv: TextView): TA<*> {
        val animationDown = TAnimation.Builder(tv)
            .translate(0f, 0f, 0.05f, -0.05f)
            .valueComputer(TAnimation.Companion.REVERSE)
            .itemDuration(200)
            .create()
        animationDown!!.repeatCount = ValueAnimator.INFINITE
        animationDown.repeatMode = ValueAnimator.REVERSE
        return BaseAnimation2IA(animationDown)
    }

    private fun createSRILO(tv: TextView): TA<*> {
        val duration: Long = 400
        val queen = TAnimationQueen(tv)
        val animation = TAnimation.Builder(tv)
            .translate(4f, 0f, 0f, 0f)
            .itemDuration(duration)
            .alpha(0f, 1f)
            .valueComputer(SquenceComputer(duration)) //                .valueComputer(TAnimation.REVERSE)
            .create()
        queen.addAnimation(animation)
        val toAni = TAnimation.Builder(tv)
            .translate(0f, -4f, 0f, 0f)
            .itemDuration(duration)
            .alpha(1f, 0f)
            .valueComputer(SquenceComputer(duration))
            .create()
        queen.addAnimation(toAni)
        queen.interpolator = null
        queen.repeatMode = ValueAnimator.RESTART
        queen.repeatCount = ValueAnimator.INFINITE
        return BaseAnimation2IA(queen)
    }
}