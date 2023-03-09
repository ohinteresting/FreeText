package com.lltvcn.freefont.core.data

import android.graphics.BlurMaskFilter.Blur
import com.lltvcn.freefont.core.annotation.Description

class BlurParam {
    @Description(name = "半径")
    var radius = 0f

    @Description(name = "模糊方式", cls = Blur::class)
    var blur: String? = null
}