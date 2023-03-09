package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.annotation.Img
import android.graphics.Shader.TileMode
import com.lltvcn.freefont.core.annotation.Description

/**
 * Created by zhaolei on 2017/10/18.
 */
class ShaderBitmapParam : IShaderData {
    @kotlin.jvm.JvmField
    @Description(name = "图片名称", cls = Img::class)
    var img: String? = null

    @Description(name = "水平方向重复方式", cls = TileMode::class)
    var tileModeX: String? = null

    @Description(name = "竖直方向重复方式", cls = TileMode::class)
    var tileModeY: String? = null

    //    @Description(name = "图片高(默认为1,字体大小)")
    //    public Float height;
    //
    //    @Description(name = "图片宽(默认为1,字体大小)")
    //    public Float width;
    override fun toShaderParam(): ShaderParam {
        val shaderParam = ShaderParam()
        shaderParam.bitmapParam = this
        return shaderParam
    }
}