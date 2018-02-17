package com.azazellj.cropyourlife

import android.annotation.TargetApi
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Camera
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import java.io.IOException

/**
 * Created by azazellj on 2/12/18.
 */

class CameraPreview : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    private val mHolder: SurfaceHolder = holder
    private var mCamera: Camera? = null

    init {

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder.addCallback(this)
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        setOnClickListener({ mCamera!!.autoFocus { success, camera -> camera.cancelAutoFocus() } })


        systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }


    fun setCamera(camera: Camera) {
        mCamera = camera


    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        if (mCamera == null) return

//        try {
            mCamera!!.setPreviewDisplay(holder)
        mCamera!!.setFaceDetectionListener { faces, camera -> {

        } }
            mCamera!!.startPreview()
//        } catch (e: IOException) {
//            Log.d(TAG, "Error setting camera preview: " + e.message)
//        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.surface == null || mCamera == null) {
            // preview surface does not exist
            return
        }


        // stop preview before making changes
        try {
            mCamera!!.stopPreview()
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera!!.setPreviewDisplay(mHolder)
            mCamera!!.startPreview()

        } catch (e: Exception) {
            Log.d(TAG, "Error starting camera preview: " + e.message)
        }

    }

    fun changeToNewCamera(newCamera: Camera) {

        // stop preview before making changes
        try {
            mCamera!!.stopPreview()
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
        }


        mCamera!!.release()



        mCamera = newCamera
        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera!!.setPreviewDisplay(mHolder)
            mCamera!!.startPreview()

        } catch (e: Exception) {
            Log.d(TAG, "Error starting camera preview: " + e.message)
        }
    }

}