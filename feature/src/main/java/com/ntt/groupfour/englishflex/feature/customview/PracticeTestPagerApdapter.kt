package com.ntt.groupfour.englishflex.feature.customview

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.ntt.groupfour.englishflex.feature.model.SpeechData

class PracticeTestPagerApdapter(fragmentMager: FragmentManager, private val speechDatas: ArrayList<SpeechData>) : FragmentStatePagerAdapter(fragmentMager) {

    val TAG = "PracticeTe"


    override fun getItem(position: Int): Fragment {
        Log.i(TAG, "override fun getItem(position: Int): Fragment {" )
        return PracticeTestFragment.newInstance(speechDatas[position])
    }

    override fun getCount(): Int {
        Log.i(TAG, "override fun getCount(): Int {" + speechDatas.size)
        return speechDatas.size
    }

}