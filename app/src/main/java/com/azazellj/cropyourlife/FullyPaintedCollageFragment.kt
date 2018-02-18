package com.azazellj.cropyourlife

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fully_painted_collage.*

class FullyPaintedCollageFragment : BottomSheetDialogFragment() {

    private lateinit var bitmaps: Array<Bitmap>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_fully_painted_collage, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirm.setOnClickListener({ confirmFilter() })
    }

    private fun confirmFilter() {

    }


    interface OnFilterConfirm {
        fun onConfirm(filter: FilterType)
    }
}