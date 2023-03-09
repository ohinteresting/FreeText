package com.lltvcn.freefont.core.data

import android.graphics.Shader.TileMode
import com.lltvcn.freefont.core.annotation.Description

/**
 * Created by zhaolei on 2017/10/18.
 */
class ShadeRadiusParam : IShaderData {
    @Description(name = "圆心水平相对坐标")
    var centerX = 0f

    @Description(name = "圆心竖直相对坐标")
    var centerY = 0f

    @Description(name = "半径")
    var radius = 0f

    @Description(name = "渐变颜色")
    var colors: Array<String?>? = null

    @Description(name = "渐变位置")
    var positions: FloatArray? = null

    @Description(name = "重复模式", cls = TileMode::class)
    var tileMode: String? = null
    override fun toShaderParam(): ShaderParam {
        val shaderParam = ShaderParam()
        shaderParam.radiusParam = this
        return shaderParam
    }
}