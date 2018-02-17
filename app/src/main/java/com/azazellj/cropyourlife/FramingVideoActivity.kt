package com.azazellj.cropyourlife

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_framing_video.*

class FramingVideoActivity : AppCompatActivity() {

    private val file = CameraHelper.getMockedVideo()
    private lateinit var durations: IntArray
    private var maxDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_framing_video)


        loadFrames()
        addViews()
    }

    private fun addViews() {
        for (index in 0 until durations.size) {
            val preview = VideoFramePreview(this)
            preview.file = file
            preview.index = index
            preview.listener = object : OnCurrentFrameTimeChanged {
                override fun frameTimeChanged(position: Int, index: Int) {
                    durations[index] = position
                    preview.redrawPreview(position)
                }
            }
            parentView.addView(preview)
            preview.post {
                preview.setMaxTime(maxDuration)
                preview.redrawPreview(durations[index])
            }
        }

        val textView = AppCompatTextView(this)
        textView.text = "Next"
        textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).also { it.topMargin = 100 }
        textView.textSize = 100f
        textView.setOnClickListener({
            Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show()
        })
        parentView.addView(textView)
    }

    fun loadFrames() {
        durations = CameraHelper.getTimeSections(file)
        maxDuration = CameraHelper.getDuration(file)
    }
}
