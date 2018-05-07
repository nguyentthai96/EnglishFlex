package com.ntt.groupfour.englishflex.feature.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.customview.PracticeRecognitionContainerLayout
import com.ntt.groupfour.englishflex.feature.customview.PracticeTestPagerApdapter
import com.ntt.groupfour.englishflex.feature.service.DataHelper

class MainActivity : AppCompatActivity() {
    val TAG = "MainAct"
    lateinit var practiveReg: PracticeRecognitionContainerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        practiveReg = findViewById(R.id.practiveReg)
        practiveReg = PracticeRecognitionContainerLayout(this, supportFragmentManager)
    }
}