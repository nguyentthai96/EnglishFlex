package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.service.DataHelper

class PracticeRecognitionContainerLayout : FrameLayout {

    val TAG = "Practices"
    private lateinit var fragmentManager: FragmentManager
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PracticeTestPagerApdapter

    constructor(context: Context?, fragmentManager: FragmentManager) : super(context) {
        Log.i(TAG, "constructor(context: Context?, fragmentManager: FragmentManager) ")
        this.fragmentManager = fragmentManager
        getVIewUI(this.context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.viewpager_multi_practice_test_layout, this)
        Log.d(TAG, "constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)")
    }


    private fun getVIewUI(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.viewpager_multi_practice_test_layout, this)
        this.viewPager = findViewById<ViewPager>(R.id.viewPager)

        val data=DataHelper().getSpeechData()
        Log.i(TAG, data.toString())
        this.pagerAdapter = PracticeTestPagerApdapter(this.fragmentManager, data)
        this.viewPager.adapter = this.pagerAdapter
        this.viewPager.refreshDrawableState()
        this.viewPager.currentItem = 2
    }


}