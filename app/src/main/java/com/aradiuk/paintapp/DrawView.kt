package com.aradiuk.paintapp

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import java.util.*

class DrawView : View, View.OnTouchListener {
    companion object {
        var DEFAULT_POINT_RADIUS = 30.0F
        val DEFAULT_COLOR: Int = android.graphics.Color.RED
        val DEFAULT_BG_COLOR: Int = android.graphics.Color.WHITE
    }

    private var paint: Paint = Paint()
    private var fingerPoint: FingerPoint? = null
    private var pointF: PointF? = null
    private var currentColor = DEFAULT_COLOR
    var currentPointRadius = DEFAULT_POINT_RADIUS
    private var currentBackgroundColor = DEFAULT_BG_COLOR
    private var fingerPoints = ArrayList<FingerPoint>();

    private var embossMF: MaskFilter? =
        EmbossMaskFilter(floatArrayOf(4f, 4f, 55f), 0.2f, 0.4f, 13.5f)
    private var blurMF: MaskFilter? = BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)

    private var emboss = false
    private var blur = false

    constructor(context: Context?) : super(context) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        this.pointF = PointF()
        this.pointF!!.x = event!!.getX()
        this.pointF!!.y = event!!.getY()


        this.fingerPoint = FingerPoint(
            this.currentColor, emboss, blur, currentPointRadius,
            pointF!!, this.eraser
        )

        this.fingerPoints.add(fingerPoint!!)
        invalidate()
        return true
    }

    var momentPoint: PointF? = null


    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawColor(currentBackgroundColor)
        for (fp in this.fingerPoints) {
            this.paint = Paint()
            this.paint!!.color = fp.color

            if (fp.emboss) {
                paint!!.maskFilter = embossMF
            } else if (fp.blur) {
                paint!!.maskFilter = blurMF
            }

            canvas.drawCircle(fp.pointF.x, fp.pointF.y, fp.pointRadius, paint!!)

        }


    }


    fun normal() {
        emboss = false
        blur = false
    }

    fun emboss() {
        emboss = true
        blur = false
    }

    fun blur() {
        emboss = false
        blur = true
    }

    fun clear() {
        this.fingerPoints.clear()
    }

    var eraser = false

    private var previousColor = 0

    fun changeColor(color: Int) {
        eraser = false
        currentColor = color
        invalidate()

    }

    fun changeBackGroundColor(color: Int) {
        if (eraser == true) {
            this.currentColor = color
            for (fp in this.fingerPoints) {
                if (fp.eraser == true) {
                    fp.color = color
                }
            }
        }
        this.currentBackgroundColor = color
        invalidate()
    }

    fun eraser() {
        if (eraser == false) {
            eraser = true
            previousColor = currentColor
            currentColor = currentBackgroundColor

        } else {
            eraser = false
            currentColor = previousColor
        }


    }
}