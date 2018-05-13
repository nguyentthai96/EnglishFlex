package com.ntt.groupfour.englishflex.feature.utils

import android.app.Activity
import android.net.Uri
import android.util.Log

class AppPreference protected constructor() {

    val TAG = "AppPrefe"
    @Volatile
    private var INSTANCE: AppPreference? = null

    // PROPERTY
    // Lever select in app
    //  level 1 Normal
    //  level 1.5 Hardly
    //  level 2 Very Hardly
    var appCurrentLevel: Float = AppConstants.LEVEL_NORMAL
    //
    var timePracticeTest: Int = 0

    lateinit var activityCurrentName: String
    lateinit var activityCurrent: Activity

    lateinit var fileUri: Uri

    init {
    }

    private object Holder {
        val INSTANCE = AppPreference()
    }

    companion object {
        @JvmStatic
        fun getInstance(): AppPreference? {
            return Holder.INSTANCE
        }
    }

    // METHOD DATA
    fun setNormalLevelPractive() {
        this.appCurrentLevel = AppConstants.LEVEL_NORMAL
    }

    fun setHardlylLevelPractive() {
        this.appCurrentLevel = AppConstants.LEVEL_HARDLY
    }

    fun setVeryHardlylLevelPractive() {
        this.appCurrentLevel = AppConstants.LEVEL_HARDLY_VERY
    }

    fun getCurrentLevelPractive(): Float {
        return this.appCurrentLevel
    }

    fun setTimerPractive(timer: Int) {
        this.timePracticeTest = timer
    }

    fun getCurrentTimerPractive(): Int {
        return this.timePracticeTest
    }

    // time practice sample real
    fun getNumberWordPracticeTest(): Int {
        var timeRead = this.timePracticeTest * AppConstants.PER_TIME_OF_WORD;
        Log.d(TAG, "getNumberWordPracticeTest time:: $timeRead timeSelect::: ${this.timePracticeTest}")
        return timeRead + (timeRead * AppConstants.TIME_RESERVE).toInt()
    }

    fun setActivityCurrent(activity:Activity, name:String){
        this.activityCurrent=activity
        this.activityCurrentName=name
    }

    fun getFileUriData():Uri{
        return this.fileUri
    }
}