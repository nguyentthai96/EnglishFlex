package com.ntt.groupfour.englishflex.feature.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.callback.LayoutCallback
import com.ntt.groupfour.englishflex.feature.customview.DialogCustoms
import com.ntt.groupfour.englishflex.feature.customview.PracticeRecognitionLayout
import com.ntt.groupfour.englishflex.feature.customview.PracticeSetupLayout
import com.ntt.groupfour.englishflex.feature.utils.AppPreference
import kotlinx.android.synthetic.main.recognition_practices_layout.*

class MainActivity : AppCompatActivity(), LayoutCallback, DialogCustoms.OnCallBack {

    val TAG = "MainAct"

    companion object {
        val REQUEST_CODE = 341
    }

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

        AppPreference.getInstance()?.setActivityCurrent(this, TAG)
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

    // Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.import_data ->{
                showDialogLoadData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    var diag : DialogCustoms=DialogCustoms.newInstance(2)
    fun  showDialogLoadData(){
        diag.setOnCallBack(this)
        diag.show(fragmentManager,"DialogImportData")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult ")

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var uri: Uri = data!!.data

            AppPreference.getInstance()?.fileUri = uri
            Log.d(TAG, "onActivityResult open file :: uri $uri")
        }
    }

    fun startSearchFileChoose() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)  // ACTION_OPEN_DOCUMENT   ACTION_OPEN_DOCUMENT_TREE
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onClickButtonChooseFile() {
        this.startSearchFileChoose()
        this.diag.dismiss()
    }
    // \Menu
}