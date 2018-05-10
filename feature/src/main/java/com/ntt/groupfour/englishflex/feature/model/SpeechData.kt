package com.ntt.groupfour.englishflex.feature.model

import com.ntt.groupfour.englishflex.feature.utils.AppConstants

class SpeechData {

    var inputSpeech: String = ""
    // time speech record data, count down
    var time: Int = 0;
    // current addRecognizeResponseBest for new state score
    var scoreCurrent: Int = 0
    var recognitionSpeechs: ArrayList<RecognitionSpeech>

    constructor(inputSpeech: String) {
        this.inputSpeech = inputSpeech
        this.time = calculateTimeReadInput()
        this.recognitionSpeechs = ArrayList()
    }

    private fun calculateTimeReadInput(): Int {
        var numberWord = this.inputSpeech.split(' ').size;
        val timeReal = numberWord * AppConstants.PER_TIME_OF_WORD
        return timeReal + (timeReal * AppConstants.TIME_RESERVE).toInt()
    }

    // add state response return best text math
    fun addRecognizeResponseBest(resultRecognitions: ArrayList<String>): String {
        var newRecogn: ArrayList<RecognitionSpeech> = ArrayList()

        for (item in resultRecognitions) {
            newRecogn.add(RecognitionSpeech(this.inputSpeech, item))
        }
        this.recognitionSpeechs.addAll(newRecogn)
        val bestItem = getBestResult(newRecogn)
        this.scoreCurrent = bestItem?.score ?: 0
        return bestItem?.textRecognition ?: "NULL-NOT_MATCH"  // TODO NTT return item max score
    }

    fun getBestResult(recognitionSpeechs: ArrayList<RecognitionSpeech>): RecognitionSpeech? {
        return recognitionSpeechs.maxBy { item -> item.score }
    }

    fun getBestResult(): RecognitionSpeech? {
        return getBestResult(this.recognitionSpeechs)
    }

    override fun toString(): String {
        return "\nSpeechData(inputSpeech='$inputSpeech', time=$time) \ninside:: $recognitionSpeechs"
    }


}