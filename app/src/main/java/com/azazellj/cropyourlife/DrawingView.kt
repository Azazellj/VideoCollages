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


    var screenRatio: Float = 9f.div(16f)
    //    private var originalImage: Bitmap? = null
//    var newBitmap: Bitmap? = null
//
    var percentValue: Float = 0f
//    var rectCropped: Rect? = null
//    var translateX: Int = 0
//    var paint: Paint? = null
//    var cm: ColorMatrix? = null
//    var filter: ColorMatrixColorFilter? = null
//    val cmData = floatArrayOf(
//            0.3f, 0.59f, 0.11f, 0f, 0f,
//            0.3f, 0.59f, 0.11f, 0f, 0f,
//            0.3f, 0.59f, 0.11f, 0f, 0f,
//            0f, 0f, 0f, 1f, 0f)

    var paint: Paint? = null
    var paint2: Paint? = null
    var bitmap: Bitmap? = null
    var rect: Rect? = null
    var rect2: Rect? = null

    var cmData = floatArrayOf(0.3f, 0.59f, 0.11f, 0f, 0f, 0.3f, 0.59f, 0.11f, 0f, 0f, 0.3f, 0.59f, 0.11f, 0f, 0f, 0f, 0f, 0f, 1f, 0f)

    var cm: ColorMatrix? = null
    var cm2: ColorMatrix? = null
    var filter: ColorFilter? = null
    var filter2: ColorFilter? = null
    var icon: Bitmap? = null


    init {
//        cm = ColorMatrix(cmData)
//        filter = ColorMatrixColorFilter(cm)
//        paint = Paint(Paint.ANTI_ALIAS_FLAG)
//        paint!!.style = Paint.Style.FILL_AND_STROKE
//        paint!!.colorFilter = filter
//
//
//
////        setImageDrawable(ContextCompat.getDrawable(context, R.drawable._maxresdefault))
//
//
//
//
//
//        originalImage = BitmapFactory.decodeResource(resources, R.drawable._maxresdefault)
////        originalImageView.setImageBitmap(originalImage)
//        newBitmap = originalImage!!.copy(Bitmap.Config.ARGB_4444, true)
//        percentValue = 0.50f
//        translateX = (percentValue * originalImage!!.width).toInt()
//        rectCropped = Rect(translateX, 0, originalImage!!.width - translateX, originalImage!!.height)

        rect2 = Rect(0, 0, 700, 1000)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.style = Paint.Style.FILL_AND_STROKE
        paint2 = Paint(Paint.ANTI_ALIAS_FLAG)
        paint2!!.style = Paint.Style.FILL

        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable._maxresdefault)


        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        screenRatio = screenWidth / screenHeight





        cm = ColorMatrix(cmData)
        filter = ColorMatrixColorFilter(cm)

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

        val bitmapRatio = viewWidth / bitmap!!.width

        val newImageWidth = measuredWidth
        val newImageHeight = (bitmap!!.height * bitmapRatio).toInt()


        icon = Bitmap.createScaledBitmap(bitmap, newImageWidth, newImageHeight, false)



        bitmap = icon

        rect = Rect(0, 0, bitmap!!.width, bitmap!!.height)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawARGB(80, 102, 204, 255)

        canvas.save()
        canvas.drawBitmap(icon, 0f, 0f, null)
        paint!!.colorFilter = filter
        canvas.drawBitmap(icon, rect2, rect2, paint)
        canvas.restore()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val currentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = (screenRatio * currentWidth).toInt()
        val measuredHeightSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.UNSPECIFIED)
//        super.onMeasure(widthMeasureSpec, measuredHeightSpec)
        setMeasuredDimension(widthMeasureSpec, measuredHeightSpec)
    }
}