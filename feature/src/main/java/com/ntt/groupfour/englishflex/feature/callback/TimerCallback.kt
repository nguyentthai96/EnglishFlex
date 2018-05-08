package com.ntt.groupfour.englishflex.feature.callback

interface TimerCallback {
    fun startTimerCount(time:Long)
    fun updateNextPracticeViewCallback()
}