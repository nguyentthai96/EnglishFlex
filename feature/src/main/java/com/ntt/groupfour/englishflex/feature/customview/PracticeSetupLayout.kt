package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.callback.LayoutCallback

class PracticeSetupLayout : LinearLayout {

    private var buttonStart: ImageView


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.setup_practice_layout, this)

        this.buttonStart = findViewById(R.id.buttonStart)
        this.buttonStart.setOnClickListener(View.OnClickListener { view ->
            onStartCallback?.onStartCallback()
        })
    }

    private lateinit var onStartCallback: LayoutCallback
    fun setOnStartCallback(onStartCallback: LayoutCallback) {
        this.onStartCallback = onStartCallback
    }
}