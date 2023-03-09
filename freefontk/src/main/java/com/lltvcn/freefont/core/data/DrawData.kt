package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.annotation.Img
import com.lltvcn.freefont.core.annotation.Description
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/10/11.
 */
class DrawData {
    @kotlin.jvm.JvmField
    var backLayers: ArrayList<LineData>? = null
    @kotlin.jvm.JvmField
    var foreLayers: ArrayList<LineData>? = null
    @kotlin.jvm.JvmField
    var layers: ArrayList<LayerData>? = null
    @kotlin.jvm.JvmField
    var width: Float? = null
    @kotlin.jvm.JvmField
    var height: Float? = null

    @kotlin.jvm.JvmField
    @Description(name = "图片名称", cls = Img::class)
    var bgImg: String? = null

    @kotlin.jvm.JvmField
    @Description(name = "颜色")
    var bgColor: String? = null
    @kotlin.jvm.JvmField
    var fontStyle: String? = null
    @kotlin.jvm.JvmField
    var shaderParam: ShaderParam? = null

    //    public AniData aniData;
    @kotlin.jvm.JvmField
    var aniType: Int? = null
}