package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.data.LayerData
import com.lltvcn.freefont.core.data.ShaderParam
import android.graphics.BlurMaskFilter.Blur
import com.lltvcn.freefont.core.data.IndexParam
import com.lltvcn.freefont.core.data.LayerData.PaintParam
import com.lltvcn.freefont.core.data.StokeParam
import com.lltvcn.freefont.core.data.BlurParam
import com.lltvcn.freefont.core.data.ShadowParam
import android.graphics.Paint.Join
import com.lltvcn.freefont.core.data.ShadeRadiusParam
import com.lltvcn.freefont.core.data.ShaderBitmapParam
import com.lltvcn.freefont.core.data.ShaderLinearParam
import com.lltvcn.freefont.core.data.ShaderSweepParam
import android.graphics.Shader.TileMode
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/10/12.
 */
object LayerDataFactory {
    fun createTxtLayer(): LayerData {
        val layerData = LayerData()
        layerData.type = LayerData.Companion.TYPE_TXT
        return layerData
    }

    fun createImgLayer(imgs: Array<String>?, rule: IndexParam.Rule): LayerData {
        val layerData = LayerData()
        layerData.type = LayerData.Companion.TYPE_IMG
        layerData.imgs = IndexParam()
        layerData.imgs!!.datas = imgs
        layerData.imgs!!.rule = rule.name
        return layerData
    }

    fun createImgLayer(
        imgs: Array<String>?,
        color: Array<String>?,
        rule: IndexParam.Rule?,
        ruleColor: IndexParam.Rule?
    ): LayerData {
        val layerData = LayerData()
        layerData.type = LayerData.Companion.TYPE_IMG
        if (imgs != null && rule != null) {
            layerData.imgs = IndexParam()
            layerData.imgs!!.datas = imgs
            layerData.imgs!!.rule = rule.name
        }
        if (color != null && ruleColor != null) {
            layerData.colors = IndexParam()
            layerData.colors!!.datas = color
            layerData.colors!!.rule = ruleColor.name
        }
        return layerData
    }

    @kotlin.jvm.JvmStatic
    fun createTxtLayerBuilder(): LayerBuilder {
        val layerData = LayerData()
        layerData.type = LayerData.Companion.TYPE_TXT
        return LayerBuilder(layerData)
    }

    @kotlin.jvm.JvmStatic
    fun createImgLayerBuilder(imgs: Array<String>?, rule: IndexParam.Rule): LayerBuilder {
        val layerData = LayerData()
        layerData.type = LayerData.Companion.TYPE_IMG
        layerData.imgs = IndexParam()
        layerData.imgs!!.datas = imgs
        layerData.imgs!!.rule = rule.name
        return LayerBuilder(layerData)
    }

    fun createLinerShaderParam(
        x0: Float,
        y0: Float,
        x1: Float,
        y1: Float,
        color0: String?,
        color1: String?,
        tile: TileMode
    ): ShaderLinearParam {
        val shaderParam = ShaderLinearParam()
        shaderParam.x0 = x0
        shaderParam.x1 = x1
        shaderParam.y0 = y0
        shaderParam.y1 = y1
        shaderParam.colors = arrayOfNulls(2)
        shaderParam.colors!![0] = color0
        shaderParam.colors!![1] = color1
        shaderParam.tileMode = tile.name
        return shaderParam
    }

    fun createLinerShaderParam(
        x0: Float,
        y0: Float,
        x1: Float,
        y1: Float,
        colors: Array<String?>?,
        positions: FloatArray?,
        tileMode: TileMode
    ): ShaderLinearParam {
        val shaderParam = ShaderLinearParam()
        shaderParam.x0 = x0
        shaderParam.x1 = x1
        shaderParam.y0 = y0
        shaderParam.y1 = y1
        shaderParam.colors = colors
        shaderParam.positions = positions
        shaderParam.tileMode = tileMode.name
        return shaderParam
    }

    fun createSweepShaderParam(
        cx: Float,
        cy: Float,
        color0: String?,
        color1: String?
    ): ShaderSweepParam {
        val shaderParam = ShaderSweepParam()
        shaderParam.centerX = cx
        shaderParam.centerY = cy
        shaderParam.colors = arrayOfNulls(2)
        shaderParam.colors!![0] = color0
        shaderParam.colors!![1] = color1
        return shaderParam
    }

    fun createSweepShaderParam(
        cx: Float,
        cy: Float,
        colors: Array<String?>?,
        positions: FloatArray?
    ): ShaderSweepParam {
        val shaderParam = ShaderSweepParam()
        shaderParam.centerX = cx
        shaderParam.centerY = cy
        shaderParam.colors = colors
        shaderParam.positions = positions
        return shaderParam
    }

    fun createRadialShaderParam(
        centerX: Float,
        centerY: Float,
        radius: Float,
        colorStart: String?,
        colorEnd: String?,
        tileMode: TileMode
    ): ShadeRadiusParam {
        val shaderParam = ShadeRadiusParam()
        shaderParam.centerX = centerX
        shaderParam.centerY = centerY
        shaderParam.radius = radius
        shaderParam.colors = arrayOfNulls(2)
        shaderParam.colors!![0] = colorStart
        shaderParam.colors!![1] = colorEnd
        shaderParam.tileMode = tileMode.name
        return shaderParam
    }

    fun createRadialShaderParam(
        centerX: Float,
        centerY: Float,
        radius: Float,
        colors: Array<String?>?,
        stops: FloatArray?,
        tileMode: TileMode
    ): ShadeRadiusParam {
        val shaderParam = ShadeRadiusParam()
        shaderParam.centerX = centerX
        shaderParam.centerY = centerY
        shaderParam.radius = radius
        shaderParam.colors = colors
        shaderParam.positions = stops
        shaderParam.tileMode = tileMode.name
        return shaderParam
    }

    fun createBitmapShaderParam(
        imgName: String?,
        tileX: TileMode,
        tileY: TileMode
    ): ShaderBitmapParam {
        val shaderParam = ShaderBitmapParam()
        shaderParam.img = imgName
        shaderParam.tileModeX = tileX.name
        shaderParam.tileModeY = tileY.name
        return shaderParam
    }

    class LayerBuilder(private val layerData: LayerData) {
        fun imgs(imgs: Array<String>?, rule: IndexParam.Rule): LayerBuilder {
            if (layerData.imgs == null) {
                layerData.imgs = IndexParam()
            }
            layerData.imgs!!.datas = imgs
            layerData.imgs!!.rule = rule.name
            return this
        }

        fun add(layer: LayerData?): LayerBuilder {
            if (layerData.layerDatas == null) {
                layerData.layerDatas = ArrayList()
            }
            layerData.layerDatas!!.add(layer)
            return this
        }

        fun offset(x: Float, y: Float): LayerBuilder {
            layerData.offsetX = x
            layerData.offsetY = y
            return this
        }

        fun rotate(degree: Float): LayerBuilder {
            layerData.degree = degree
            return this
        }

        fun scale(scale: Float): LayerBuilder {
            layerData.scale = scale
            return this
        }

        fun color(color: String?): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.color = color
            return this
        }

        fun size(size: Float): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.relativeSize = size
            return this
        }

        fun font(fontName: String?): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.font = fontName
            return this
        }

        fun fontStyle(style: FontStyle): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.fontStyle = style.name
            return this
        }

        fun stoke(width: Float, join: Join): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.stokeParam = StokeParam()
            layerData.paintParam!!.stokeParam!!.join = join.name
            layerData.paintParam!!.stokeParam!!.width = width
            //            layerData.paintParam.style =
            return this
        }

        fun shadow(radius: Float, dx: Float, dy: Float, shadowColor: String?): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.shadowParam = ShadowParam(radius, dx, dy, shadowColor)
            return this
        }

        fun blur(radius: Float, blur: Blur): LayerBuilder {
            preparePaintParam()
            layerData.paintParam!!.blurParam = BlurParam()
            layerData.paintParam!!.blurParam!!.blur = blur.name
            layerData.paintParam!!.blurParam!!.radius = radius
            return this
        }

        fun linearGradient(
            x0: Float,
            y0: Float,
            x1: Float,
            y1: Float,
            color0: String?,
            color1: String?,
            tileMode: TileMode
        ): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.linearParam =
                createLinerShaderParam(x0, y0, x1, y1, color0, color1, tileMode)
            return this
        }

        fun linearGradient(
            x0: Float,
            y0: Float,
            x1: Float,
            y1: Float,
            colors: Array<String?>?,
            positions: FloatArray?,
            tileMode: TileMode
        ): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.linearParam =
                createLinerShaderParam(x0, y0, x1, y1, colors, positions, tileMode)
            return this
        }

        fun sweepGradient(cx: Float, cy: Float, color0: String?, color1: String?): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.sweepParam =
                createSweepShaderParam(cx, cy, color0, color1)
            return this
        }

        fun sweepGradient(
            cx: Float,
            cy: Float,
            colors: Array<String?>?,
            positions: FloatArray?
        ): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.sweepParam =
                createSweepShaderParam(cx, cy, colors, positions)
            return this
        }

        fun radialGradient(
            centerX: Float,
            centerY: Float,
            radius: Float,
            centerColor: String?,
            edgeColor: String?,
            tileMode: TileMode
        ): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.radiusParam = createRadialShaderParam(
                centerX,
                centerY,
                radius,
                centerColor,
                centerColor,
                tileMode
            )
            return this
        }

        fun radialGradient(
            centerX: Float,
            centerY: Float,
            radius: Float,
            colors: Array<String?>?,
            stops: FloatArray?,
            tileMode: TileMode
        ): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.radiusParam =
                createRadialShaderParam(centerX, centerY, radius, colors, stops, tileMode)
            return this
        }

        fun bitmapShader(imgName: String?, tileX: TileMode, tileY: TileMode): LayerBuilder {
            prepareShaderParam()
            layerData.paintParam!!.shaderParam!!.bitmapParam =
                createBitmapShaderParam(imgName, tileX, tileY)
            return this
        }

        fun create(): LayerData {
            return layerData
        }

        private fun prepareShaderParam() {
            preparePaintParam()
            if (layerData.paintParam!!.shaderParam == null) {
                layerData.paintParam!!.shaderParam = ShaderParam()
            }
        }

        private fun preparePaintParam() {
            if (layerData.paintParam == null) {
                layerData.paintParam = PaintParam()
            }
        }
    }
}