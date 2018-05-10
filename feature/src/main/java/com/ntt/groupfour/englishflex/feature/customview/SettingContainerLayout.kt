package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.utils.AppConstants
import com.ntt.groupfour.englishflex.feature.utils.AppPreference

class SettingContainerLayout : LinearLayout {

    val TAG="SettingContainer"

    private val LEVEL_PRACTICE = arrayOf("Normal", "Hardly", "Very Hardly")
    private val TIMES_PRACTICE = arrayOf("3 minutes", "5 minutes", "10 minutes", "15 minutes")
    private var spinnerLevel: AutoCompleteSpinner
    private var spinnerTimer: AutoCompleteSpinner

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.setting_layout, this)

        var adapterLevel: ArrayAdapter<String> = ArrayAdapter(this.context, android.R.layout.simple_dropdown_item_1line, LEVEL_PRACTICE)
        this.spinnerLevel = findViewById(R.id.spinnerLevel)
        spinnerLevel.setAdapter(adapterLevel)
        spinnerLevel.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "spinnerLevel.onItemClickListener ::: level $position ")

            when (position) {
                0 -> AppPreference.Companion.getInstance()?.setNormalLevelPractive()
                1 -> AppPreference.Companion.getInstance()?.setHardlylLevelPractive()
                2 -> AppPreference.Companion.getInstance()?.setVeryHardlylLevelPractive()
            }
        }

        var adapterTime: ArrayAdapter<String> = ArrayAdapter(this.context, android.R.layout.simple_dropdown_item_1line, TIMES_PRACTICE)
        spinnerTimer = findViewById(R.id.spinnerTimer)
        spinnerTimer.setAdapter(adapterTime)
        spinnerTimer.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "spinnerTimer.onItemClickListener ::: timer $position")

            when (position) {
                0 -> AppPreference.Companion.getInstance()?.setTimerPractive(3)
                1 -> AppPreference.Companion.getInstance()?.setTimerPractive(5)
                2 -> AppPreference.Companion.getInstance()?.setTimerPractive(10)
                3 -> AppPreference.Companion.getInstance()?.setTimerPractive(15)
            }
        }

//        showCurrentSettingView()
    }

    fun showCurrentSettingView() {
        val level = AppPreference.getInstance()?.getCurrentLevelPractive()
        when (level) {
            AppConstants.LEVEL_NORMAL -> this.spinnerLevel.setText(LEVEL_PRACTICE[0])
            AppConstants.LEVEL_HARDLY -> this.spinnerLevel.setText(LEVEL_PRACTICE[1])
            AppConstants.LEVEL_HARDLY_VERY -> this.spinnerLevel.setText(LEVEL_PRACTICE[2])
        }

        val timer: Int? = AppPreference.getInstance()?.getCurrentTimerPractive()
        when (timer) {
            3 -> this.spinnerTimer.setText(TIMES_PRACTICE[0])
            5 -> this.spinnerTimer.setText(TIMES_PRACTICE[1])
            10 -> this.spinnerTimer.setText(TIMES_PRACTICE[2])
            15 -> this.spinnerTimer.setText(TIMES_PRACTICE[3])
        }

        Log.d(TAG, "showCurrentSettingView get from Preference ::: level $level  timer $timer")
    }
}