package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.annotation.Img

import android.graphics.*
import com.lltvcn.freefont.core.annotation.Description
import com.lltvcn.freefont.core.linedrawer.*

/**
 * Created by zhaolei on 2017/10/17.
 */
class LineData {
    @Description(name = "相对高度")
    var rh = 0f

    @Description(name = "位置", cls = Gravity::class)
    var gravity: String? = null

    @kotlin.jvm.JvmField
    @Description(name = "图片名称", cls = Img::class)
    var bitmap: String? = null

    @Description(name = "颜色", cls = Color::class)
    var color: Int? = null
}