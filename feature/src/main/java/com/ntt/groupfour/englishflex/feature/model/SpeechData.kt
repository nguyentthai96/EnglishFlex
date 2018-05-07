package com.ntt.groupfour.englishflex.feature.model

import com.ntt.groupfour.englishflex.feature.utils.AppConstants
import java.util.*

class SpeechData {

    var inputSpeech: String = ""
    // time speech record data, count down
    var time: Int = 0;
    lateinit var recognitionSpeechs: ArrayList<RecognitionSpeech>

    constructor(inputSpeech: String) {
        this.inputSpeech = inputSpeech
        this.time = calculateTimeReadInput()
    }

    private fun calculateTimeReadInput(): Int {
        var numberWord = this.inputSpeech.split(' ').size;
        return numberWord * AppConstants.PER_TIME_OF_WORD
    }

    fun addRecognizeResult(resultRecognitions: ArrayList<String>) {
        for (item in resultRecognitions) {
            this.recognitionSpeechs.add(RecognitionSpeech(this.inputSpeech, item))
        }
    }

    fun getTextResultRecognize() {
    }

    override fun toString(): String {
        return "SpeechData(inputSpeech='$inputSpeech', time=$time)"
    }


}