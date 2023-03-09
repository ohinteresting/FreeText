package com.lltvcn.freefont.core.animation

/**
 * Created by zhaolei on 2017/12/6.
 */
class BaseAnimation2IA(override val value: BaseAnimation?) : TA<BaseAnimation?> {
    override fun start() {
        value!!.start()
    }

    override fun stop() {
        value!!.end()
    }

    override val duration: Long
        get() = value!!.duration
}