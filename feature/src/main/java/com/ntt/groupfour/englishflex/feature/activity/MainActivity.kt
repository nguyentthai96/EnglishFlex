package com.ntt.groupfour.englishflex.feature.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.callback.LayoutCallback
import com.ntt.groupfour.englishflex.feature.customview.PracticeRecognitionLayout
import com.ntt.groupfour.englishflex.feature.customview.PracticeSetupLayout
import kotlinx.android.synthetic.main.recognition_practices_layout.*

class MainActivity : AppCompatActivity(), LayoutCallback {

    val TAG = "MainAct"
    lateinit var practicesRecoginition: PracticeRecognitionLayout
    lateinit var practiceSetupLayout: PracticeSetupLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.practiceSetupLayout = findViewById(R.id.practiceSetupLayout)
        this.practiceSetupLayout.visibility = View.VISIBLE
        this.practiceSetupLayout.setOnStartCallback(this)

        this.practicesRecoginition = findViewById(R.id.practicesRecoginition)
        this.practicesRecoginition.visibility = View.GONE
        this.practicesRecoginition.setOnStartCallback(this)
    }

    override fun onStartCallback() {
        requestPermisionOS()
    }

    fun permissionAllow() {
        this.practiceSetupLayout.visibility = View.GONE
        this.practicesRecoginition.visibility = View.VISIBLE
        this.practicesRecoginition.createViewUIAndConfig()
        this.practicesRecoginition.reshowSettingView()
    }

    fun permissionDenice() {
        Toast.makeText(this@MainActivity, "Permission Denied!", Toast.LENGTH_SHORT).show()
    }

    // NTT Activity contain Speech Recogniton
    private val REQUEST_RECORD_PERMISSION = 100

    private fun requestPermisionOS() {
        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_RECORD_PERMISSION -> if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                this.permissionAllow()
            } else {
                this.permissionDenice()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        this.practicesRecoginition.stopActivity()
//        this.speechContainerLayout.destroyServiceRecognition()  // TODO NTT change to stop???
        Log.i(TAG, "onStop() destroy");
    }
// \NTT Activity contain Speech Recogniton
}