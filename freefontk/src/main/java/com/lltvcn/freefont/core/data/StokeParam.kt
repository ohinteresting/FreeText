package com.lltvcn.freefont.core.data

import android.graphics.Paint.Join
import com.lltvcn.freefont.core.annotation.Description

class StokeParam {
    @Description(name = "描边宽度")
    var width = 0f

    @Description(name = "边角锐度", cls = Join::class)
    var join: String? = null
}