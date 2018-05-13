package com.ntt.groupfour.englishflex.feature.service

import android.util.Log
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.utils.AppPreference

class DataHelper {

    val TAG="DataHelper"

    private fun getDataListString(): ArrayList<String>? {
        val datas = FileStoreService().readTextFile(AppPreference.getInstance()!!.getFileUriData()).toString()
        Log.d(TAG, "Data load form file $datas")
        var result: ArrayList<String> = ArrayList()
        for (item in datas.split('\n')) {
            result.add(item.toString())
        }

        return result
//        return arrayListOf(
//                "About when?",
//                "what's your name",
//                "Add fuel to the fire.",
//                "Hell with haggling",
//                "Is that so",
//                "Love me love my dog",
//                "What the hell is going on?"
//        )
    }


    fun getSpeechData(): ArrayList<SpeechData> {
        var result: ArrayList<SpeechData> = ArrayList()
        var datas = this.getDataListString()

        for (item in datas!!) {
            result.add(SpeechData(item))
        }
        return result
    }

    fun getStandarScore(datas: ArrayList<SpeechData>): Int {
        var score = 0
        for (item in datas) {
            score += item.inputSpeech.split(' ').size
        }
        return score
    }
}