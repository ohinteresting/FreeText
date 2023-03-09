package com.lltvcn.freefont.core.data

import com.lltvcn.freefont.core.annotation.Description

class ShadowParam {
    @Description(name = "半径")
    var radius = 0f

    @Description(name = "水平偏移")
    var x = 0f

    @Description(name = "竖直偏移")
    var y = 0f

    @Description(name = "阴影颜色")
    var color: String? = null

    constructor() {}
    constructor(radius: Float, x: Float, y: Float, color: String?) {
        this.radius = radius
        this.x = x
        this.y = y
        this.color = color
    }
}