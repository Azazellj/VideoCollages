package com.azazellj.cropyourlife

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Created by azazellj on 2/6/18.
 */
class DrawingView : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @SuppressLint("NewApi")
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var screenRatio: Float = 16f.div(9f)
    var percentValue: Float = 0f

    var paint: Paint? = null
    var originalBitmap: Bitmap
    var icon: Bitmap? = null

    var rect2: Rect? = null

    var cmData: FilterType = FilterType.INVERTED

    var colorMatrix: ColorMatrix? = null
    var colorMatrixFilter: ColorFilter? = null


    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.style = Paint.Style.FILL_AND_STROKE

        originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable._maxresdefault)

        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        screenRatio = screenWidth / screenHeight

        colorMatrix = ColorMatrix(cmData.value)
        colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
        paint!!.colorFilter = colorMatrixFilter
    }

    fun setPercentage(percentage: Float) {
        this.percentValue = percentage

        rect2 = Rect(0, 0, (percentage * icon!!.width).toInt(), icon!!.height)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post { initScaledImage() }
    }

    fun initScaledImage() {
        val viewWidth = measuredWidth.toFloat()
        val viewHeight = measuredHeight.toFloat()
        val bitmapRatio = viewWidth / originalBitmap.width

        val newImageWidth = measuredWidth
        val newImageHeight = (originalBitmap.height * bitmapRatio).toInt()

        icon = Bitmap.createScaledBitmap(originalBitmap, newImageWidth, newImageHeight, false)
        rect2 = Rect(0, 0, icon!!.width, icon!!.height)

        requestLayout();
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.save()
        canvas.drawBitmap(icon, 0f, 0f, null)
        canvas.drawBitmap(icon, rect2, rect2, paint)
        canvas.restore()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val currentWidth = MeasureSpec.getSize(widthMeasureSpec)
        var measuredHeight = (screenRatio * currentWidth).toInt()

        if (icon != null) if (icon!!.height < measuredHeight) measuredHeight = icon!!.height

        val measuredHeightSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.UNSPECIFIED)
        setMeasuredDimension(widthMeasureSpec, measuredHeightSpec)
    }
}