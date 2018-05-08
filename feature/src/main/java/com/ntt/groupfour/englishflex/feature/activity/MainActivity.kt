package com.ntt.groupfour.englishflex.feature.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.callback.TimerCallback
import com.ntt.groupfour.englishflex.feature.customview.SpeechContainerLayout
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.service.DataHelper

class MainActivity : AppCompatActivity(), TimerCallback {


    val TAG = "MainAct"
    lateinit var speechContainerLayout: SpeechContainerLayout
    lateinit var buttonStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.speechContainerLayout = findViewById(R.id.practiveReg)
        this.speechContainerLayout.createViewUIAndConfig()
        this.speechContainerLayout.setOnTimerCallback(this)

        this.buttonStart = findViewById(R.id.buttonStart)
        this.buttonStart.setOnClickListener(View.OnClickListener {
            //            requestkPermisionOS()
//            speechContainerLayout.startPracticeTest()
            addNextPractice()
        })

    }

    private var indexPracticeCurrent = 0
    private var dataPractice: ArrayList<SpeechData> = DataHelper().getSpeechData(); // get from singleton

    // return null if current last item
    private fun getNextPractice(): SpeechData? {
        if (indexPracticeCurrent < dataPractice.size - 1) {
            this.indexPracticeCurrent++;
            return this.dataPractice[indexPracticeCurrent]
        }
        return null
    }

    fun addNextPractice() {
        try {
            this.speechContainerLayout.updateDataView(this.getNextPractice()!!)  // throw KotlinNullPointerException when null
            this.speechContainerLayout.startPracticeTest()
        } catch (nullEx: KotlinNullPointerException) {
            Log.e(TAG, "Error catch :: $nullEx")
        }
    }


    // NTT Activity contain Speech Recogniton
    private val REQUEST_RECORD_PERMISSION = 100

    private fun requestkPermisionOS() {
        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_RECORD_PERMISSION -> if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                this.speechContainerLayout.startPracticeTest()
            } else {
                Toast.makeText(this@MainActivity, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        this.speechContainerLayout.destroyServiceRecognition()  // TODO NTT change to stop???
        Log.i(TAG, "onStop() destroy");
    }
    // \NTT Activity contain Speech Recogniton


    override fun startTimerCount(time: Long) {

    }

    override fun updateNextPracticeViewCallback() {
        addNextPractice()
    }
}