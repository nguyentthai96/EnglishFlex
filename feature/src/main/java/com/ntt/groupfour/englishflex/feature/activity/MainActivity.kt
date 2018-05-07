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
import com.ntt.groupfour.englishflex.feature.customview.PracticeContainerLayout

class MainActivity : AppCompatActivity() {
    val TAG = "MainAct"
    lateinit var practiveReg: PracticeContainerLayout
    lateinit var buttonStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.practiveReg = findViewById(R.id.practiveReg)
        this.practiveReg.createViewUI(this.supportFragmentManager)


        this.buttonStart = findViewById(R.id.buttonStart)

        this.buttonStart.setOnClickListener(View.OnClickListener {
            checkPermisionOS()
        })

    }


    private val REQUEST_RECORD_PERMISSION = 100
    private fun checkPermisionOS() {
        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_RECORD_PERMISSION -> if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                this.practiveReg.startPracticeTest()
            } else {
                Toast.makeText(this@MainActivity, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        this.practiveReg.destroyServiceRecognition()  // TODO NTT change to stop???
        Log.i(TAG, "onStop() destroy");
    }
}