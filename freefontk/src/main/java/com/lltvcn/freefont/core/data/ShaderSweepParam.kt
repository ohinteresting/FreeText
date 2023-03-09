package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.annotation.Description

/**
 * Created by zhaolei on 2017/10/18.
 */
class ShaderSweepParam : IShaderData {
    @Description(name = "圆心X相对坐标")
    var centerX = 0f

    @Description(name = "圆心Y相对坐标")
    var centerY = 0f

    @Description(name = "渐变颜色")
    var colors: Array<String?>? = null

    @Description(name = "渐变位置")
    var positions: FloatArray? = null
    override fun toShaderParam(): ShaderParam {
        val shaderParam = ShaderParam()
        shaderParam.sweepParam = this
        return shaderParam
    }
}