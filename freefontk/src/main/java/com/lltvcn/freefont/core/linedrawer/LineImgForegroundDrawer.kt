package com.lltvcn.freefont.core.linedrawer

import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable

/**
 * Created by zhaolei on 2017/9/20.
 */
class LineImgForegroundDrawer : LineImgDrawer, ForegroundDrawer {
    constructor(bm: Bitmap?, relativeHeight: Float, gravity: Gravity) : super(
        BitmapDrawable(bm),
        relativeHeight,
        gravity
    ) {
    }

    constructor(drawable: Drawable, relativeHeight: Float, gravity: Gravity) : super(
        drawable,
        relativeHeight,
        gravity
    ) {
    }
}