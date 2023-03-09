package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.data.LayerData.DispatchDrawParam

/**
 * Created by zhaolei on 2017/10/24.
 */
interface IDispatchDraw {
    fun toDispatchDrawParam(): DispatchDrawParam
}