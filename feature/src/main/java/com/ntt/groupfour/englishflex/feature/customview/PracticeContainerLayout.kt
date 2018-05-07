package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.service.DataHelper
import com.ntt.groupfour.englishflex.feature.utils.RecognitionUtils

class PracticeContainerLayout : FrameLayout, RecognitionListener {

    val TAG = "Practices"
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PracticePagerApdapter

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.container_multi_practice_layout, this)
    }


    fun createViewUI(fragmentManager: FragmentManager) {
//        val view= View.inflate(context, R.layout.container_multi_practice_layout, (rootView as FrameLayout).findViewById(R.id.containerLayout))
        this.viewPager = findViewById<ViewPager>(R.id.viewPager)

        val data = DataHelper().getSpeechData()
        Log.i(TAG, data.toString())
        this.pagerAdapter = PracticePagerApdapter(fragmentManager, data)
        this.viewPager.adapter = this.pagerAdapter
        this.viewPager.refreshDrawableState()
        this.viewPager.currentItem = 2
    }

    private var speech: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    override fun onReadyForSpeech(params: Bundle?) {
        Log.i(TAG, "onReadyForSpeech after setIntenRecognitonSpeech");
        // NTT TODO record service start...
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i(TAG, "onRmsChanged: $rmsdB")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.i(TAG, "onBufferReceived: " + buffer);
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.i(TAG, "onPartialResults");
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.i(TAG, "onEvent");
    }

    override fun onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
    }

    override fun onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech");

        // NTT TODO record service stop...
    }


    // NTT

    override fun onError(error: Int) {
        val errorMessage = RecognitionUtils.getErrorText(error)
        Log.e(TAG, "onError runtime, not error code FAILED $errorMessage")

    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")
        val matches = RecognitionUtils.getArrayStringResult(results);
        Log.i(TAG, "onResults :::: " + matches)
    }

    private fun setIntenRecognitonSpeech() {
        this.speech = SpeechRecognizer.createSpeechRecognizer(this.context)
        Log.i(TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this.context))
        this.speech?.setRecognitionListener(this)
        this.recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        this.recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en")
        this.recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        this.recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
    }

    fun startPracticeTest() {
        setIntenRecognitonSpeech()
        speech?.startListening(recognizerIntent)
    }

    fun destroyServiceRecognition() {
        if (this.speech != null) {
            this.speech?.destroy()

            Log.i(TAG, "destroyServiceRecognition  destroy");
        }
    }

    fun stopRecognitionListening() {
        if (this.speech != null) {
            this.speech?.stopListening()

            Log.i(TAG, "stopRecognitionListening  stop");
        }
    }

}