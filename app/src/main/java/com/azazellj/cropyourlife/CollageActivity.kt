package com.azazellj.cropyourlife

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.media.MediaScannerConnection
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_collage.*
import java.io.FileOutputStream


class CollageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collage)

        save.setOnClickListener({ saveImageToFile() })
    }


    private lateinit var canvasBitmap: Bitmap
    private lateinit var canvas: Canvas

    fun saveImageToFile() {
        //todo request permissions
        val bitmaps: Array<Bitmap> = arrayOf(i1.originalBitmap, i2.originalBitmap, i3.originalBitmap, i4.originalBitmap, i5.originalBitmap)
        val canvasBitmapWidth: Int = bitmaps[0].width + bitmaps[1].width
        val canvasBitmapHeight: Int = bitmaps[0].height + bitmaps[2].height * 2 + bitmaps[3].height
        canvasBitmap = Bitmap.createBitmap(canvasBitmapWidth, canvasBitmapHeight, Bitmap.Config.ARGB_8888)

        canvas = Canvas(canvasBitmap)
        canvas.drawBitmap(bitmaps[0], 0f, 0f, null)
        canvas.drawBitmap(bitmaps[1], bitmaps[0].width.toFloat(), 0f, null)
        val dstRect = Rect(0, bitmaps[0].height, bitmaps[2].width * 2, bitmaps[0].height + bitmaps[2].height * 2)
        canvas.drawBitmap(bitmaps[2], null, dstRect, null)
        canvas.drawBitmap(bitmaps[3], 0f, bitmaps[0].height.toFloat() + bitmaps[2].height.toFloat() * 2, null)
        canvas.drawBitmap(bitmaps[4], bitmaps[3].width.toFloat(), bitmaps[0].height.toFloat() + bitmaps[2].height.toFloat() * 2, null)

        val file = CameraHelper.getOutputMediaFile(CameraHelper.MEDIA_TYPE_IMAGE)
        if (file!!.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            canvasBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            Toast.makeText(applicationContext, "Saved Sucessfully", Toast.LENGTH_LONG).show()

            MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null)
            { path, uri ->
                Log.i("ExternalStorage", "Scanned $path:")
                Log.i("ExternalStorage", "-> uri=" + uri)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }
}
