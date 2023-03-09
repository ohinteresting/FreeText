package com.lltvcn.freefont.core.data

import java.util.*

/**
 * Created by zhaolei on 2017/12/6.
 */
class AniData {
    var type = 0
    var fromX: Float? = null
    var toX: Float? = null
    var fromY: Float? = null
    var toY: Float? = null
    var fromAlpha: Float? = null
    var toAlpha: Float? = null
    var fromScaleX: Float? = null
    var toScaleX: Float? = null
    var fromScaleY: Float? = null
    var toScaleY: Float? = null
    var fromDegree: Float? = null
    var toDegree: Float? = null
    var aniMode = 0
    var aniParam: HashMap<String, String>? = null
    var duration: Long = 0
    var repeatMode = 0
    var repeatCount = 0
    var startDelay = 0
    var anis: ArrayList<AniData>? = null

    companion object {
        const val TYPE_ALL = 0
        const val TYPE_SINGLE_TXT = 1
        const val MODE_SEQUENCE = 0
    }
}