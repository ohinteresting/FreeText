package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.data.ShaderParam
import android.graphics.BlurMaskFilter.Blur
import com.lltvcn.freefont.core.data.LayerData.PaintParam
import android.graphics.Paint.Join
import android.graphics.Shader.TileMode
import com.lltvcn.freefont.core.util.CU
import kotlin.jvm.JvmOverloads
import android.text.TextUtils
import com.lltvcn.freefont.core.layer.ILayer.IPaintHandler
import android.graphics.*
import android.util.Log
import java.lang.RuntimeException

/**
 * Created by zhaolei on 2017/10/11.
 */
class PaintHandler @JvmOverloads constructor(
    private val paintParam: PaintParam?,
    private val loader: SourceLoader<Bitmap?>? = null,
    private val fontLoader: SourceLoader<Typeface?>? = null
) : IPaintHandler {
    private var maskFilter: BlurMaskFilter? = null
    private var shader: Shader? = null
    private var matrix: Matrix? = null
    private var shadeRect: RectF? = null
    override fun handlePaint(index: Int, paint: Paint, rectF: RectF?) {
        val textSize = paint.textSize
        val alpha = paint.alpha / 255f
        if (!TextUtils.isEmpty(paintParam!!.color)) {
            paint.color = CU.toInt(paintParam.color)
        } else if (paintParam.colors != null && paintParam.colors!!.available()) {
            paint.color = CU.toInt(paintParam.colors!!.getDataByIndex(index))
        }
        paint.alpha = (alpha * paint.alpha).toInt()
        if (!TextUtils.isEmpty(paintParam.style)) {
            paint.style = Paint.Style.valueOf(paintParam.style!!)
        }
        if (paintParam.stokeParam != null) {
            if (alpha > 0) {
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = paintParam.stokeParam!!.width * textSize
                paint.strokeJoin = Join.valueOf(paintParam.stokeParam!!.join!!)
            } else {
                paint.strokeWidth = 0f
            }
        }
        if (paintParam.relativeSize != null) {
            paint.textSize = paintParam.relativeSize!! * textSize
        }
        if (!TextUtils.isEmpty(paintParam.font)) {
            paint.typeface = fontLoader!!.loadByName(paintParam.font!!)
        }
        if (!TextUtils.isEmpty(paintParam.fontStyle)) {
            paint.typeface = Typeface.create(
                paint.typeface, FontStyle.valueOf(
                    paintParam.fontStyle!!
                ).ordinal
            )
        }
        if (paintParam.shadowParam != null) {
            paint.setShadowLayer(
                paintParam.shadowParam!!.radius * textSize,
                paintParam.shadowParam!!.x * textSize,
                paintParam.shadowParam!!.y * textSize,
                CU.toInt(
                    paintParam.shadowParam!!.color
                )
            )
        }
        if (maskFilter != null) {
            paint.maskFilter = maskFilter
        }
        if (shader != null) {
            matrix!!.reset()
            Log.i("jjjjkkk", "handlePaint")
            matrix!!.setRectToRect(shadeRect, rectF, Matrix.ScaleToFit.FILL)
            //            matrix.setTranslate(rectF.left,rectF.top);
            shader?.setLocalMatrix(matrix)
            paint.shader = shader
        }
    }

    private fun generalShaderByParam(param: ShaderParam?): Shader? {
        var shader: Shader? = null
        if (param!!.bitmapParam != null) {
            if (loader != null) {
                val bitmapParam = param.bitmapParam
                val bm = loader.loadByName(bitmapParam!!.img!!)
                if (bm != null) {
//                    Bitmap tBm = Bitmap.createBitmap((int) S,(int) S, Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(tBm);
//                    Rect rect = new Rect();
//                    float scale = 0;
//                    if(bm.getWidth()>bm.getHeight()){
//                        int offset = (bm.getWidth()-bm.getHeight())/2;
//                        rect.set(offset,0,bm.getWidth()-offset,bm.getHeight());
//                    }else{
//                        int offset = (bm.getHeight()-bm.getWidth())/2;
//                        rect.set(0,offset,bm.getWidth(),bm.getHeight()-offset);
//                    }
//                    canvas.drawBitmap(bm,rect,S.R,null);
                    shadeRect = RectF(0f, 0f, bm.width.toFloat(), bm.height.toFloat())
                    shader = BitmapShader(
                        bm, TileMode.valueOf(bitmapParam.tileModeX!!), TileMode.valueOf(
                            bitmapParam.tileModeY!!
                        )
                    )
                }
            }
        }
        if (param.sweepParam != null) {
            // TODO: 2017/10/12 这里没有考虑字的宽度来调整圆心位置
            if (param.sweepParam!!.colors == null || param.sweepParam!!.colors!!.size < 2) {
                throw RuntimeException("LinearGradient param wrong!")
            }
            shader =
                if ((param.sweepParam!!.positions == null || param.sweepParam!!.positions!!.size == 0) && param.sweepParam!!.colors!!.size == 2) {
                    SweepGradient(
                        param.sweepParam!!.centerX * S, param.sweepParam!!.centerY * S, CU.toInt(
                            param.sweepParam!!.colors!![0]
                        ), CU.toInt(param.sweepParam!!.colors!![1])
                    )
                } else {
                    SweepGradient(
                        param.sweepParam!!.centerX * S, param.sweepParam!!.centerY * S, CU.toInt(
                            param.sweepParam!!.colors
                        ), param.sweepParam!!.positions
                    )
                }
            shadeRect = R
        }
        if (param.linearParam != null) {
            if (param.linearParam!!.colors == null || param.linearParam!!.colors!!.size < 2) {
                throw RuntimeException("LinearGradient param wrong!")
            }
            shader =
                if ((param.linearParam!!.positions == null || param.linearParam!!.positions!!.size == 0) && param.linearParam!!.colors!!.size == 2) {
                    LinearGradient(
                        param.linearParam!!.x0 * S,
                        param.linearParam!!.y0 * S,
                        param.linearParam!!.x1 * S,
                        param.linearParam!!.y1 * S,
                        CU.toInt(
                            param.linearParam!!.colors!![0]
                        ),
                        CU.toInt(param.linearParam!!.colors!![1]),
                        TileMode.valueOf(
                            param.linearParam!!.tileMode!!
                        )
                    )
                } else {
                    LinearGradient(
                        param.linearParam!!.x0 * S,
                        param.linearParam!!.y0 * S,
                        param.linearParam!!.x1 * S,
                        param.linearParam!!.y1 * S,
                        CU.toInt(param.linearParam!!.colors),
                        param.linearParam!!.positions,
                        TileMode.valueOf(
                            param.linearParam!!.tileMode!!
                        )
                    )
                }
            shadeRect = R
        }
        if (param.radiusParam != null) {
            // TODO: 2017/10/12 这里没有考虑字的宽度来调整圆心位置
            if (param.radiusParam!!.colors == null || param.radiusParam!!.colors!!.size < 2) {
                throw RuntimeException("LinearGradient param wrong!")
            }
            shader =
                if ((param.radiusParam!!.positions == null || param.radiusParam!!.positions!!.size == 0) && param.radiusParam!!.colors!!.size == 2) {
                    RadialGradient(
                        param.radiusParam!!.centerX * S,
                        param.radiusParam!!.centerY * S,
                        param.radiusParam!!.radius * S,
                        CU.toInt(
                            param.radiusParam!!.colors!![0]
                        ),
                        CU.toInt(param.radiusParam!!.colors!![1]),
                        TileMode.valueOf(
                            param.radiusParam!!.tileMode!!
                        )
                    )
                } else {
                    RadialGradient(
                        param.radiusParam!!.centerX * S,
                        param.radiusParam!!.centerY * S,
                        param.radiusParam!!.radius * S,
                        CU.toInt(
                            param.radiusParam!!.colors
                        ),
                        param.radiusParam!!.positions,
                        TileMode.valueOf(
                            param.radiusParam!!.tileMode!!
                        )
                    )
                }
            shadeRect = R
        }
        return shader
    }

    companion object {
        const val S = 300f
        val R = RectF(0f, 0f, S, S)
    }

    init {
        if (paintParam != null) {
            if (paintParam.blurParam != null) {
                maskFilter = BlurMaskFilter(
                    paintParam.blurParam!!.radius * S, Blur.valueOf(
                        paintParam.blurParam!!.blur!!
                    )
                )
            }
            if (paintParam.shaderParam != null) {
                shader = generalShaderByParam(paintParam.shaderParam)
            }
            if (shader != null) {
                matrix = Matrix()
            }
        }
    }
}