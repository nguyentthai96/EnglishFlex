package com.ntt.groupfour.englishflex.feature.customview

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.ntt.groupfour.englishflex.feature.model.SpeechData

class PracticePagerApdapter(fragmentMager: FragmentManager, private val speechDatas: ArrayList<SpeechData>) : FragmentStatePagerAdapter(fragmentMager) {

    val TAG = "PracticePag"

    override fun getItem(position: Int): Fragment {
        Log.i(TAG, "override fun getItem(position: Int): Fragment")
        val item = PracticeTestFragment.newInstance(speechDatas[position]);
        return item
    }

    override fun getCount(): Int {
        Log.i(TAG, "override fun getCount(): Int  ::: " + speechDatas.size)
        return speechDatas.size
    }
}