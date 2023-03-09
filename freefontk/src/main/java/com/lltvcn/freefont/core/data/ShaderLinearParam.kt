package com.lltvcn.freefont.core.data


import android.graphics.Shader.TileMode
import com.lltvcn.freefont.core.annotation.Description

/**
 * Created by zhaolei on 2017/10/18.
 */
class ShaderLinearParam : IShaderData {
    @Description(name = "起点水平相对坐标")
    var x0 = 0f

    @Description(name = "终点水平相对坐标")
    var x1 = 0f

    @Description(name = "起点竖直相对坐标")
    var y0 = 0f

    @Description(name = "终点竖直相对坐标")
    var y1 = 0f

    @Description(name = "渐变颜色")
    var colors: Array<String?>? = null

    @Description(name = "渐变位置")
    var positions: FloatArray? = null

    @Description(name = "重复模式", cls = TileMode::class)
    var tileMode: String? = null
    override fun toShaderParam(): ShaderParam {
        val shaderParam = ShaderParam()
        shaderParam.linearParam = this
        return shaderParam
    }
}