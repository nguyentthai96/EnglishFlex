package com.ntt.groupfour.englishflex.feature.service

import com.ntt.groupfour.englishflex.feature.model.SpeechData

class DataHelper {

    private fun getDataListString(): ArrayList<String>? {
        return arrayListOf(
                "My name",
                "what's your name"
        )
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