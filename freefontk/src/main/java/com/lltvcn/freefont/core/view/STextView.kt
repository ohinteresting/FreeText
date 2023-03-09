package com.lltvcn.freefont.core.view

import com.lltvcn.freefont.core.data.LayerData
import com.lltvcn.freefont.core.data.IndexParam
import com.lltvcn.freefont.core.data.LayerData.PaintParam
import com.lltvcn.freefont.core.util.CU
import android.graphics.drawable.Drawable
import kotlin.jvm.JvmOverloads
import com.lltvcn.freefont.core.animation.TA
import android.text.SpannableStringBuilder
import com.lltvcn.freefont.core.data.DrawData
import android.graphics.RectF
import android.text.TextUtils
import com.lltvcn.freefont.core.animation.A
import com.lltvcn.freefont.core.animation.BaseAnimation2IA
import com.lltvcn.freefont.core.animation.ICanvasTransform
import com.lltvcn.freefont.core.animation.Animation2IA
import android.text.Spanned
import android.graphics.drawable.ColorDrawable
import com.lltvcn.freefont.core.linedrawer.LineImgForegroundDrawer
import com.lltvcn.freefont.core.linedrawer.LineImgBackgroundDrawer
import android.graphics.drawable.BitmapDrawable
import android.graphics.Typeface
import com.lltvcn.freefont.core.layer.ImgLayer.DrawableLoader
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.util.LruCache
import android.view.Gravity
import com.lltvcn.freefont.core.layer.*
import java.io.File

/**
 * Created by zhaolei on 2017/10/18.
 */
class STextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ShadeTextView(context, attrs) {
    private var outTxt: CharSequence? = null
    private var sourcePath: String? = null
    var tAnimation: TA<*>? = null
        private set

    override fun setText(text: CharSequence, type: BufferType) {
        outTxt = text
        if (drawData != null) {
            val builder = buildString(drawData!!, outTxt)
            super.setText(builder, type)
        } else {
            super.setText(text, type)
        }
    }

    private var drawData: DrawData? = null
    fun setData(data: DrawData?) {
        drawData = data
        if (tAnimation != null) {
            tAnimation!!.stop()
            clearAnimation()
            tAnimation = null
        }
        notifyDataChange()
    }

    fun notifyDataChange() {
        handleData(drawData)
        if (outTxt != null && outTxt!!.isNotEmpty()) {
            text = outTxt
        }
        postInvalidate()
    }

    private fun handleData(drawData: DrawData?) {
        if (drawData == null) return
        //        if(drawData.color !=null)
//            setTextColor(drawData.color);
//        if(drawData.size !=null)
//            setTextSize(TypedValue.COMPLEX_UNIT_PX,drawData.size);
        if (drawData.shaderParam != null) {
            val param = PaintParam()
            param.shaderParam = drawData.shaderParam
            post {
                PaintHandler(param, bitmapLoader, fontLoader).handlePaint(
                    0,
                    paint,
                    RectF(0f, 0f, width.toFloat(), height.toFloat())
                )
            }
        } else {
            paint.shader = null
        }
        //        Typeface typeface = null;
//        Log.i("jjiis","hasBitmap"+drawData.bgImg);
//        if(!TextUtils.isEmpty(drawData.font)){
//            Log.i("jjiis","font"+drawData.font);
//            typeface = fontLoader.loadByName(drawData.font);
//        }
        if (!TextUtils.isEmpty(drawData.fontStyle)) {
            setTypeface(typeface, FontStyle.valueOf(drawData.fontStyle!!).ordinal)
        } else {
            setTypeface(typeface)
        }
        //        else {
//            setTypeface(typeface);
//        }
//        tv.setTypeface(Typeface.createFromAsset(getAssets(),"08华康娃娃体W5.TTF"));
        if (!TextUtils.isEmpty(drawData.bgImg)) {
            Log.i("jjiis", "hasBitmap" + drawData.bgImg)
            val drawable = drawableLoader.loadByName(drawData.bgImg!!)
            if (!TextUtils.isEmpty(drawData.bgColor)) {
                CU.filterDrawable(drawable, CU.toInt(drawData.bgColor))
            }
            setBackgroundDrawable(drawable)
        } else if (!TextUtils.isEmpty(drawData.bgColor)) {
            setBackgroundColor(CU.toInt(drawData.bgColor))
        } else {
            setBackgroundDrawable(null)
        }
        gravity = Gravity.CENTER
    }

    private fun buildString(drawData: DrawData, content: CharSequence?): SpannableStringBuilder {
        val width: Float = if (drawData.width == null) 0f else drawData.width!!
        val height = if (drawData.height == null) 1.0f else drawData.height!!
        val span = LayerSpan(height, width)
        val builder = SpannableStringBuilder(content)
        if (drawData.aniType != null) {
            tAnimation = A.createTAByType(drawData.aniType!!, this)
            if (tAnimation != null) {
                if (tAnimation is BaseAnimation2IA) {
                    span.setCanvasTransform((tAnimation as BaseAnimation2IA).value as ICanvasTransform)
                } else if (tAnimation is Animation2IA) {
                    animation = (tAnimation as Animation2IA).value
                }
            }
        }
        if (drawData.layers != null && drawData.layers!!.size > 0) {
            var layer: ILayer? = null
            for (layerData in drawData.layers!!) {
                layer = createLayer(layerData)
                span.addLayer(layer)
            }
            builder.setSpan(span, 0, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (drawData.foreLayers != null && drawData.foreLayers!!.size > 0) {
            for (lineData in drawData.foreLayers!!) {
                var drawable: Drawable? = null
                if (!TextUtils.isEmpty(lineData.bitmap)) {
                    drawable = drawableLoader.loadByName(lineData.bitmap!!)
                    if (lineData.color != null) {
                        CU.filterDrawable(drawable, lineData.color!!)
                    }
                } else if (lineData.color != null) {
                    drawable = ColorDrawable(lineData.color!!)
                }
                if (drawable != null) {
                    val drawer = LineImgForegroundDrawer(
                        drawable, lineData.rh, com.lltvcn.freefont.core.linedrawer.Gravity.valueOf(
                            lineData.gravity!!
                        )
                    )
                    builder.setSpan(drawer, 0, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        if (drawData.backLayers != null && drawData.backLayers!!.size > 0) {
            for (lineData in drawData.backLayers!!) {
                var drawable: Drawable? = null
                if (!TextUtils.isEmpty(lineData!!.bitmap)) {
                    drawable = drawableLoader.loadByName(lineData.bitmap!!)
                    if (lineData.color != null) {
                        CU.filterDrawable(drawable, lineData.color!!)
                    }
                } else if (lineData.color != null) {
                    drawable = ColorDrawable(lineData.color!!)
                }
                if (drawable != null) {
                    val drawer = LineImgBackgroundDrawer(
                        drawable, lineData.rh, com.lltvcn.freefont.core.linedrawer.Gravity.valueOf(
                            lineData.gravity!!
                        )
                    )
                    builder.setSpan(drawer, 0, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        //        super.setText(builder);
        return builder
    }

    private fun createLayer(layerData: LayerData?): ILayer? {
        var layer: ILayer? = null
        if (layerData!!.type == LayerData.TYPE_IMG) {
            layer = ImgLayer(DrawableLoaderImpl(layerData, drawableLoader))
        } else if (layerData.type == LayerData.TYPE_TXT) {
            layer = TxtLayer()
        } else if (layerData.type == LayerData.TYPE_MULTI) {
            val mlayer = MultiLayer()
            if (layerData.layerDatas != null && layerData.layerDatas!!.size > 0) {
                for (i in layerData.layerDatas!!.indices) {
                    mlayer.add(createLayer(layerData.layerDatas!![i]))
                }
            }
            layer = mlayer
        }
        if (layerData.drawParam != null) {
            if (layerData.drawParam!!.clipParam != null) {
                layer!!.setDrawDispatcher(ClipDrawer(layerData.drawParam!!.clipParam!!.span))
            } else if (layerData.drawParam!!.offsetParam != null) {
                layer!!.setDrawDispatcher(
                    OffsetDrawer(
                        layerData.drawParam!!.offsetParam!!.positions,
                        layerData.drawParam!!.offsetParam!!.offsets
                    )
                )
            }
        }
        if (layerData.paintParam != null) {
            layer!!.setPaintHandler(PaintHandler(layerData.paintParam, bitmapLoader, fontLoader))
        }
        if (layerData.offsetX != 0f || layerData.offsetY != 0f) {
            layer!!.offset(layerData.offsetX, layerData.offsetY)
        }
        if (layerData.scale > 0 && layerData.scale != 1f) {
            layer!!.scale(layerData.scale)
        }
        if (layerData.degree != 0f) {
            layer!!.rotate(layerData.degree)
        }
        return layer
    }

    fun setLocalSourcePath(path: String) {
        sourcePath = if (path.endsWith(File.separator)) path else path + File.separator
        notifyDataChange()
    }

    private val drawableLruCache = LruCache<String, Drawable>(10)
    private val drawableLoader: SourceLoader<Drawable?> = object : SourceLoader<Drawable?> {
        override fun loadByName(name: String): Drawable? {
            var drawable = drawableLruCache[name]
            if (drawable == null) {
                drawable = Drawable.createFromPath(getLocalFileName(name))
                if (drawable != null) drawableLruCache.put(name, drawable)
            }
            return drawable
        }

        fun getLocalFileName(name: String): String? {
            return if (TextUtils.isEmpty(sourcePath)) null else sourcePath + name
        }
    }
    private val bitmapLoader = object : SourceLoader<Bitmap?> {
        override fun loadByName(name: String): Bitmap? {
            val drawable = drawableLoader.loadByName(name)
            return if (drawable != null && drawable is BitmapDrawable) {
                drawable.bitmap
            } else {
                null
            }
        }
    }

    private val fontLoader = object : SourceLoader<Typeface?> {
        override fun loadByName(name: String): Typeface? {
           return null
        }
    }

//    private val fontLoader = SourceLoader<Typeface?> {
//        //当前版本先不支持多种字体
//        null
//        //            String file = getLocalFileName(name);
////            if(!TextUtils.isEmpty(file)&&new File(file).exists()){
////                Log.i("jsi",file);
////                return Typeface.createFromFile(file);
////            }else{
////                return null;
////            }
//    }

    private inner class DrawableLoaderImpl(data: LayerData?, sourceLoader: SourceLoader<Drawable?>) :
        DrawableLoader {
        private val colors: IndexParam<String>? = data?.colors
        private val imgs: IndexParam<String>? = data?.imgs
        private val bitmapSourceLoader = sourceLoader
        override fun getDrawable(index: Int): Drawable? {
            var drawable: Drawable? = null
            if (imgs != null && imgs.available()) {
                drawable = bitmapSourceLoader.loadByName(imgs.getDataByIndex(index)!!)
                if (colors != null && colors.available()) {
                    CU.filterDrawable(drawable, CU.toInt(colors.getDataByIndex(index)))
                }
            } else {
                if (colors != null && colors.available()) {
                    drawable = ColorDrawable(CU.toInt(colors.getDataByIndex(index)))
                }
            }
            return drawable
        }

    }
}