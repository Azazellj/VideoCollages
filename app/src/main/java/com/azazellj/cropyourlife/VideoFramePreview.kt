package com.azazellj.cropyourlife

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.view_video_frame_preview.view.*
import java.io.File

/**
 * Created by azazellj on 2/12/18.
 */
class VideoFramePreview : RelativeLayout {
    var listener: OnCurrentFrameTimeChanged? = null
    var file: File? = null
    var index: Int = 0
        set(value) {
            field = value
            header.text = "Frame #$value"
        }

    private val position: Subject<Int> = PublishSubject.create()


//    var currentPosition = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_frame_preview, this, true)

        frameSeeker.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                listener?.frameTimeChanged(seekBar!!.progress, index)
//                redrawPreview(seekBar!!.progress)
            }
        })


        position
//                .throttleWithTimeout(1000L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map { CameraHelper.getFrame(it, file) }
                .map { optimizeImage(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ preview.setImageBitmap(it) })
    }


    fun setMaxTime(time: Int) {
//        frameSeeker.post {
        frameSeeker.max = time
//        }
    }

    fun redrawPreview(time: Int) {
        Log.e("TIME", time.toString())
        if (frameSeeker.progress != time) frameSeeker.progress = time
        position.onNext(time)
    }

    fun optimizeImage(bitmap: Bitmap): Bitmap {
        val width = resources.displayMetrics.widthPixels

        val newBitmapWidth = width
        val newBitmapHeight = (width / bitmap.width.toFloat()) * bitmap.height

        return Bitmap.createScaledBitmap(bitmap, newBitmapWidth, newBitmapHeight.toInt(), false)
    }

}