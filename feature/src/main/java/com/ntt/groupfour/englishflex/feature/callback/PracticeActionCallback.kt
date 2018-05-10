package com.ntt.groupfour.englishflex.feature.callback

interface PracticeActionCallback {
    fun startTimerCount(time:Long)
    fun updateNextPracticeViewCallback()
    fun showMicRecognition()
    fun showProgressBarRecognition()
}