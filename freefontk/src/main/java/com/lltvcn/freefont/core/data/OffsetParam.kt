package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.data.LayerData.DispatchDrawParam
import com.lltvcn.freefont.core.annotation.Description

/**
 * Created by zhaolei on 2017/10/24.
 */
class OffsetParam : IDispatchDraw {
    @Description(name = "位置信息，与偏移量一一对应")
    var positions: FloatArray? = null

    @Description(name = "每个位置的偏移量")
    var offsets: FloatArray? = null
    override fun toDispatchDrawParam(): DispatchDrawParam {
        val param = DispatchDrawParam()
        param.offsetParam = this
        return param
    }
}