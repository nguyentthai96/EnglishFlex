package com.ntt.groupfour.englishflex.feature.service

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.ntt.groupfour.englishflex.feature.utils.AppPreference
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FileStoreService {

    val TAG = "FileStoreService"

    fun readTextFile(uri: Uri): StringBuilder {
        val activity = AppPreference.getInstance()!!.activityCurrent

        return readTextFile(uri, activity)
    }

    private fun readTextFile(uri: Uri, activity: Activity): StringBuilder {
        Log.d(TAG, "readTextFile :: uri ")
        var reader: BufferedReader? = null
        val builder = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(activity.contentResolver.openInputStream(uri)))
            var line = reader!!.readLine()

            while (line != null) {
                builder.append(line + '\n')
                line = reader!!.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return builder
    }
}