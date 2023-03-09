package com.lltvcn.freefont.core.layer

import com.lltvcn.freefont.core.layer.ILayer.DrawParam
import android.graphics.*
import android.util.Log

/**
 * Created by zhaolei on 2017/9/26.
 */
class TxtLayer : BaseLayer() {
    public override fun drawLayer(index: Int, canvas: Canvas?, param: DrawParam?, paint: Paint) {
        if (param is ITxtDrawParam) {
            val txtParam = param.txtParam
            if (txtParam != null) {
                val metrics = paint.fontMetrics
                Log.i("draw-txt", "" + txtParam.y)
                canvas!!.drawText(
                    txtParam.text!!,
                    txtParam.start,
                    txtParam.end,
                    txtParam.x,
                    txtParam.y,
                    paint
                )
                //                canvas.drawText(txtParam.text,txtParam.start,txtParam.end,txtParam.x,txtParam.centerY-(metrics.bottom+metrics.top)/2f,paint);
            }
        }
    }

    interface ITxtDrawParam : DrawParam {
        var txtParam: TxtParam
    }

    class TxtParam {
        var x = 0f
        var y = 0f
        var centerY = 0f
        var start = 0
        var end = 0
        var text: CharSequence? = null
    }
}