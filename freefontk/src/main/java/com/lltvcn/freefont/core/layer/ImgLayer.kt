package com.lltvcn.freefont.core.layer

import android.graphics.drawable.Drawable
import com.lltvcn.freefont.core.layer.ILayer.DrawParam
import android.graphics.*

/**
 * Created by zhaolei on 2017/9/27.
 */
class ImgLayer @JvmOverloads constructor(private val loader: DrawableLoader?) : BaseLayer() {
    private val from = Rect()
    private val to = RectF()
    private val scaleX = 0f
    private val scaleY = 0f
    private val curScale = 0f
    override fun drawLayer(index: Int, c: Canvas?, param: DrawParam?, paint: Paint) {
        if (loader != null) {
            val drawable = loader.getDrawable(index)
            //            float scaleX = rect.width()/drawable.getIntrinsicWidth();
//            float scaleY = rect.height()/drawable.getIntrinsicHeight();
            if (drawable != null) {
                drawable.setBounds(
                    rect!!.left.toInt(),
                    rect!!.top.toInt(),
                    rect!!.right.toInt(),
                    rect!!.bottom.toInt()
                )
                drawable.draw(c!!)
            }
        }
        //            if(bm!=null){
//                from.set(0,0,bm.getWidth(),bm.getHeight());
//                scaleX = rect.width()/from.width();
//                scaleY = rect.height()/from.height();
//                curScale = Math.min(scaleX,scaleY);
//                to.set(0,0,from.width()*curScale,from.height()*curScale);
//                to.offset(rect.centerX()-to.centerX(),rect.centerY()-to.centerY());
//                c.drawBitmap(bm,from,to,paint);
//            }
    }

    interface DrawableLoader {
        fun getDrawable(index: Int): Drawable?
    }

    class ImgParam {
        var index = 0
    }
}