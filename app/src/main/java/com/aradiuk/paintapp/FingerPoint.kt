package com.aradiuk.paintapp

import android.graphics.Path
import android.graphics.PointF

class FingerPoint {
    var color: Int
    var emboss : Boolean
    var blur : Boolean
    var pointRadius : Float
    var pointF: PointF
    var eraser: Boolean


    constructor(color: Int, emboss: Boolean, blur: Boolean, pointRadius: Float, pointF: PointF, eraser: Boolean) {
        this.color = color
        this.emboss = emboss
        this.blur = blur
        this.pointRadius = pointRadius
        this.pointF = pointF
        this.eraser = eraser
    }
}