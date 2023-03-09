package com.lltvcn.freefont.core.data

import java.util.*

/**
 * Created by zhaolei on 2017/10/10.
 */
class LayerData {
    /**
     * type: "img" , "txt","multi" */
    @kotlin.jvm.JvmField
    var type = 0
    @kotlin.jvm.JvmField
    var name: String? = null
    @kotlin.jvm.JvmField
    var layerDatas: ArrayList<LayerData?>? = null
    @kotlin.jvm.JvmField
    var offsetX = 0f
    @kotlin.jvm.JvmField
    var offsetY = 0f
    @kotlin.jvm.JvmField
    var degree = 0f
    @kotlin.jvm.JvmField
    var scale = 0f
    @kotlin.jvm.JvmField
    var imgs: IndexParam<String>? = null
    @kotlin.jvm.JvmField
    var colors: IndexParam<String>? = null
    @kotlin.jvm.JvmField
    var paintParam: PaintParam? = null
    @kotlin.jvm.JvmField
    var drawParam: DispatchDrawParam? = null

    class DispatchDrawParam {
        @kotlin.jvm.JvmField
        var clipParam: ClipParam? = null
        @kotlin.jvm.JvmField
        var offsetParam: OffsetParam? = null
    }

    class PaintParam {
        @kotlin.jvm.JvmField
        var relativeSize: Float? = null
        @kotlin.jvm.JvmField
        var color: String? = null
        @kotlin.jvm.JvmField
        var colors: IndexParam<String?>? = null
        @kotlin.jvm.JvmField
        var font: String? = null
        var fontStyle: String? = null
        var style: String? = null
        @kotlin.jvm.JvmField
        var stokeParam: StokeParam? = null
        @kotlin.jvm.JvmField
        var blurParam: BlurParam? = null
        @kotlin.jvm.JvmField
        var shaderParam: ShaderParam? = null
        @kotlin.jvm.JvmField
        var shadowParam: ShadowParam? = null
    }

    companion object {
        const val TYPE_MULTI = 2
        const val TYPE_IMG = 1
        const val TYPE_TXT = 0
    }
}