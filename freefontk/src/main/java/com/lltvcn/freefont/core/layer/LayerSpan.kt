package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.animation.ICanvasTransform
import android.graphics.Paint.FontMetricsInt
import com.lltvcn.freefont.core.layer.TxtLayer.ITxtDrawParam
import com.lltvcn.freefont.core.layer.TxtLayer.TxtParam
import android.text.style.ReplacementSpan
import android.graphics.*
import android.util.Log
import java.util.ArrayList

/**
 * Created by zhaolei on 2017/9/21.
 */
class LayerSpan(private val rH: Float, private val rW: Float) : ReplacementSpan() {
    private val layers: ArrayList<ILayer?>
    private val matrix = Matrix()
    private val fontMetrics = FontMetricsInt()
    private val paintMetrics = FontMetricsInt()
    private var canvasTransform: ICanvasTransform? = null
    fun addLayer(layer: ILayer?) {
        layers.add(layer)
    }

    fun setCanvasTransform(transform: ICanvasTransform?) {
        canvasTransform = transform
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        if (fm != null) {
            Log.i("txt-getSize-start", fm.toString())
            val fontMetricsInt = paint.fontMetricsInt
            fm.ascent = fontMetricsInt.ascent
            fm.descent = fontMetricsInt.descent
            fm.top = fontMetricsInt.top
            fm.bottom = fontMetricsInt.bottom
            if (rH != 1f && rH != 0f) {
//                int moreH = ((int) (paint.getTextSize()*rH)-(fm.bottom-fm.top))/2;
                fm.top = (fm.top * rH).toInt()
                fm.ascent = (fm.ascent * rH).toInt()
                fm.bottom = (fm.bottom * rH).toInt()
                fm.descent = (fm.descent * rH).toInt()
            }
            fontMetrics.ascent = fm.ascent
            fontMetrics.descent = fm.descent
            fontMetrics.top = fm.top
            fontMetrics.bottom = fm.bottom
            Log.i("txt-getSize-end", fm.toString() + ":::size:" + paint.textSize)
        }
        val width = measureWidth(paint, text, start, end)
        Log.i("BaseSpanNew", "getSize:$width")
        return width
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
//        float scale = S.S /paint.getTextSize();
        Log.i("BaseSpanNew", "draw")
        Log.i("txt-draw-top", "" + top + "bottom" + bottom)
        Log.i("txt-draw", fontMetrics.toString())
        val txtSize = paint.textSize
        val rect = RectF()
        val width = measureWidth(paint, text, start, end)
        val height = bottom - top
        rect[x, (y + fontMetrics.ascent).toFloat(), width + x] = (y + fontMetrics.descent).toFloat()

//        rect.set(x,top,width+x,bottom);
//        if(scale!=1){
//            canvas.save();
//            paint.setTextSize(S.S);
////            getSize(paint,text,start,end,paint.getFontMetricsInt());
//            float tempY = y-rect.centerY();
//            canvas.scale(1f/scale,1f/scale,rect.centerX(),rect.centerY());
//            matrix.setScale(scale,scale,rect.centerX(),rect.centerY());
//            matrix.mapRect(rect);
////            canvas.scale(scale,scale,rect.centerX(),rect.centerY());
//
////            canvas.setMatrix(matrix);
////            canvas.concat(matrix);
////            canvas.scale(scale,scale,rect.centerX(),rect.centerY());
//            Shader shader = paint.getShader();
//            if(shader!=null){
//                shader.setLocalMatrix(matrix);
//            }
//            width = (int) (scale*width);
//            height = (int) (scale*height);
//            Paint.FontMetrics metrics = paint.getFontMetrics();
//            y = (int) (rect.centerY()-(metrics.descent+metrics.ascent)/2);
//            matrix.reset();
//        }
        paint.getFontMetricsInt(paintMetrics)
        val param = DrawParamI()
        param.txtParam.text = text
        if (rW > 0) {
            param.txtParam.x = (width - paint.measureText(text, start, end)) / 2 + rect.left
        } else {
            param.txtParam.x = rect.left
        }
        param.txtParam.centerY = rect.centerY()
        param.txtParam.y = rect.centerY() - (paintMetrics.descent + paintMetrics.ascent) / 2f
        //        param.txtParam.y = y;


//        if(rH>0&&rH!=1){
//            param.txtParam.y = (height-paint.getTextSize())/2+y;
//        }else{
//            param.txtParam.y = y;
//        }
        param.txtParam.start = start
        param.txtParam.end = end
        if (canvasTransform != null) {
            staticPaint.set(paint)
            canvas.save()
            canvasTransform!!.transformCanvas(start, rect, canvas, staticPaint)
            for (layer in layers) {
                layer!!.setRectF(rect, staticPaint.textSize)
                layer.draw(start, canvas, param, staticPaint)
            }
            canvas.restore()
        } else {
            for (layer in layers) {
                layer!!.setRectF(rect, paint.textSize)
                layer.draw(start, canvas, param, paint)
            }
        }
        //        if(scale!=1){
//            canvas.restore();
//            paint.setTextSize(txtSize);
//        }
    }

    private fun measureWidth(paint: Paint, text: CharSequence, start: Int, end: Int): Int {
//        float scale = paint.getTextScaleX();
//        if(rW!=1){
//            paint.setTextScaleX(rW);
//        }
//        int width = (int) paint.measureText(text,start,end);
//        paint.setTextScaleX(scale);
//        return width;
        return if (rW > 0) (rW * paint.textSize * (end - start)).toInt() else paint.measureText(
            text,
            start,
            end
        )
            .toInt()
    }

    private inner class DrawParamI : ITxtDrawParam {
        private val txt = TxtParam()
        override var txtParam: TxtParam
            get() = txt
            set(value) {}
    }

    companion object {
        private val staticPaint = Paint()
    }

    init {
        layers = ArrayList()
    }
}