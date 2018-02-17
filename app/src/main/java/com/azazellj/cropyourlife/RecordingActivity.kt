package com.azazellj.cropyourlife

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recording.*
import java.io.File
import java.io.IOException


class RecordingActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_CAMERA = 999
    }

    private var mCamera: Camera? = null
    //    private lateinit var mPreview: CameraPreview
    private var mMediaRecorder: MediaRecorder? = null
    private var mOutputFile: File? = null
    private var profile: CamcorderProfile? = null

    private var isRecording = false
    private var isFront = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)


        play.setOnClickListener({ onCaptureClick() })

        changeCamera.setOnClickListener({ changeCamera() })

        showPreview()
    }

    private fun changeCamera() {
        isFront = !isFront
//        mCamera.release()
        mCamera = getCamera()

        if (mCamera != null) mPreview.changeToNewCamera(mCamera!!)
    }

    private fun getCamera(): Camera? {
        val camera: Camera?

        if (isFront)
            camera = CameraHelper.defaultFrontFacingCameraInstance
        else
            camera = CameraHelper.defaultBackFacingCameraInstance

        if (camera == null) return null


        val parameters = camera.parameters
        val mSupportedPreviewSizes = parameters.supportedPreviewSizes
        val mSupportedVideoSizes = parameters.supportedVideoSizes
        val optimalSize = CameraHelper.getOptimalVideoSize(mSupportedVideoSizes, mSupportedPreviewSizes, resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
//
        profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH)
        profile!!.videoFrameWidth = optimalSize!!.width
        profile!!.videoFrameHeight = optimalSize.height

//        mPreview.setOnClickListener({ mCamera.autoFocus { success, camera -> camera.cancelAutoFocus() } })

        parameters.setPreviewSize(profile!!.videoFrameWidth, profile!!.videoFrameHeight)
        parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
        camera.parameters = parameters

        return camera
    }


    private fun showPreview() {
        mCamera = getCamera()
        if (mCamera == null) return

        mPreview.setCamera(mCamera!!)

//        mPreview.setCamera(mCamera)

//        try {
//            mCamera.setPreviewDisplay(mPreview)
//            mCamera.startPreview()
//        } catch (e: Exception) {
//            releaseMediaRecorder()
//            releaseCamera()
//            throw IllegalAccessException("Cant open camera")
//        }

    }


    fun onCaptureClick() {
        if (isRecording) {
            try {
                mMediaRecorder!!.stop()  // stop the recording
            } catch (e: RuntimeException) {
                mOutputFile?.delete()
            }

            releaseMediaRecorder() // release the MediaRecorder object
            mCamera!!.lock()         // take camera access back from MediaRecorder

            isRecording = false
            releaseCamera()
        } else {
//            MediaPrepareTask().execute(null, null, null)
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder!!.start()

                isRecording = true
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder()
//                return false
            }

        }
    }

    override fun onPause() {
        super.onPause()
        releaseMediaRecorder()
        releaseCamera()
    }

    private fun releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.reset()
            mMediaRecorder!!.release()
            mMediaRecorder = null
            mCamera!!.lock()
        }
    }

    private fun releaseCamera() {
//        if (true) {
        mCamera!!.release()
//            mCamera = null
//        }
    }

    private fun prepareVideoRecorder(): Boolean {

        mMediaRecorder = MediaRecorder()

        mCamera!!.unlock()
        mMediaRecorder!!.setCamera(mCamera)

        mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
        mMediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.CAMERA)
//        mMediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP)
//        mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

        mMediaRecorder!!.setProfile(profile)

//        mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)


        mOutputFile = CameraHelper.getOutputMediaFile(CameraHelper.MEDIA_TYPE_VIDEO)
        if (mOutputFile == null) {
            return false
        }
        mMediaRecorder!!.setOutputFile(mOutputFile!!.path)
        try {
            mMediaRecorder!!.prepare()
        } catch (e: IllegalStateException) {
            releaseMediaRecorder()
            return false
        } catch (e: IOException) {
            releaseMediaRecorder()
            return false
        }

        return true
    }


    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
            return
        } else {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    override fun onDestroy() {
        releaseMediaRecorder()
        releaseCamera()
        super.onDestroy()
    }
}
