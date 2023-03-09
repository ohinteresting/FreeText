package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.data.LayerData.DispatchDrawParam
import com.lltvcn.freefont.core.annotation.Description

/**
 * Created by zhaolei on 2017/10/24.
 */
class ClipParam : IDispatchDraw {
    @Description(name = "间隔高度")
    var span = 0f
    override fun toDispatchDrawParam(): DispatchDrawParam {
        val param = DispatchDrawParam()
        param.clipParam = this
        return param
    }
}