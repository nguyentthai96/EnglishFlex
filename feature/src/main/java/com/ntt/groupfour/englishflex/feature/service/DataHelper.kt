package com.ntt.groupfour.englishflex.feature.service

import com.ntt.groupfour.englishflex.feature.model.SpeechData
import java.util.*
import kotlin.collections.ArrayList

class DataHelper {

    private fun getDataListString(): ArrayList<String>? {
        return arrayListOf(
                "We frequently create classes whose main purpose is to hold data.",
                "In such a class some standard functionality and utility",
                " functions are often mechanically derivable from the data",
                "In Kotlin, this is called a data class and is marked "
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
}