package com.azazellj.cropyourlife

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_filter_preview.view.*


/**
 * Created by azazellj on 2/6/18.
 */
class FilterPreviewView : RelativeLayout, OnDragTouchListener.OnDragActionListener {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @SuppressLint("NewApi")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_filter_preview, this, true)
        dividerView.setOnTouchListener(OnDragTouchListener(dividerView, onDragActionListener = this))


    }

    override fun onDragStart(view: View?) {

    }

    override fun onDrag(view: View?, percent: Float) {

        originalImageView.setPercentage(percent)
        originalImageView.invalidate()

//        draw(percent)

    }

    override fun onDragEnd(view: View?) {

    }

    fun setOriginalImage(@DrawableRes original: Int) {

    }


    fun draw(percentage: Float) {











    }

//    override fun dispatchDraw(canvas: Canvas?) {
//        canvas!!.save();
//
////        val resultBmp = Bitmap.createBitmap(rectCropped!!.right - rectCropped!!.left, rectCropped!!.bottom - rectCropped!!.top, Bitmap.Config.ARGB_8888)
////        canvas.translate(translateX.toFloat(), 0f)
////        canvas.drawBitmap(originalImage, -rectCropped!!.left.toFloat(), -rectCropped!!.top.toFloat(), paint);
//        canvas.drawBitmap(originalImage, null, Rect(0, 0, originalImage!!.width, originalImage!!.height), paint);
//        canvas.restore();
//    }
}