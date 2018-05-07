package com.ntt.groupfour.englishflex.feature.utils

class AppPreference protected constructor() {

    @Volatile
    private var INSTANCE: AppPreference? = null

    // PROPERTY
    // Lever select in app
    //  level 1 Normal
    //  level 1.5 Hardly
    //  level 2 Very Hardly
    var appCurrentLevel: Float = AppConstants.LEVEL_NORMAL
    var timePracticeTest: Int = 0

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

    fun getNumberWordPracticeTest(): Int{
        var timeRead =  this.timePracticeTest*AppConstants.PER_TIME_OF_WORD;
        return timeRead + (timeRead*AppConstants.TIME_RESERVE ).toInt()
    }
}