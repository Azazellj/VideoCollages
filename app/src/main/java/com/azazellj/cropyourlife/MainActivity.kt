package com.azazellj.cropyourlife

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isPressed = false

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        filterPreview.setOriginalImage(R.drawable._maxresdefault)


//        elevator.setOnTouchListener { v, event ->
//            isPressed = event?.actionMasked == MotionEvent.ACTION_DOWN
//
//
//            event.rawY
//
//
//
//
//            return@setOnTouchListener false
//        }
//
//        elevator.setOnDragListener { v, event ->
//            if (!isPressed) return@setOnDragListener false
//
//            v.y = event.y
//
//
//            return@setOnDragListener false
//        }

//

    }
}
