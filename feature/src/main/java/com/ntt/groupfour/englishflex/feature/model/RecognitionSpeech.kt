package com.ntt.groupfour.englishflex.feature.model

class RecognitionSpeech {

    var textRecognition: String = ""
    // count number correct with inputs
    var score: Int = 0

    //
    protected constructor()

    constructor(textInputRecognition: String, textRecognitionOut: String) {
        this.textRecognition = textRecognitionOut
        for (word in textInputRecognition.split(' ')) {
            var textCompare = textRecognitionOut.toUpperCase();
            if (textCompare.contains(word.toUpperCase())) {
                this.score++;
            }
        }
    }

    override fun toString(): String {
        return "RecognitionSpeech(textRecognition='$textRecognition', score=$score)"
    }
}