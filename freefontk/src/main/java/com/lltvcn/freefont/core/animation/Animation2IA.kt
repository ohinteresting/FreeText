package com.lltvcn.freefont.core.animation

import android.view.animation.*

/**
 * Created by zhaolei on 2017/12/6.
 */
class Animation2IA(override val value: Animation) : TA<Animation> {
    override fun start() {
        value.start()
    }

    override fun stop() {
        value.cancel()
    }

    override val duration: Long
        get() = value.duration
}