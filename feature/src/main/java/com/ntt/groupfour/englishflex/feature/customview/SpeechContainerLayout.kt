package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.callback.TimerCallback
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.utils.RecognitionUtils

class SpeechContainerLayout : FrameLayout, RecognitionListener {

    val TAG = "SpeechConL"

    private lateinit var dataPractice: SpeechData;

    private lateinit var textInputSpeech: TextView
    private lateinit var textOutputSpeech: TextView

    private lateinit var timerCallback: TimerCallback

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_speech_recognition_layout, this)
    }

    fun createViewUIAndConfig() {
//        val view= View.inflate(context, R.layout.container_multi_practice_layout, (rootView as FrameLayout).findViewById(R.id.containerLayout))
        this.textInputSpeech = findViewById(R.id.textInputSpeech)
        this.textOutputSpeech = findViewById(R.id.textOutputSpeech)

        configRecognitionSpeech()
    }

    fun setOnTimerCallback(timerCallback: TimerCallback) {
        this.timerCallback = timerCallback
    }

    fun updateDataView(data: SpeechData) {
        this.dataPractice = data
        Log.i(TAG, dataPractice.toString())
        this.textInputSpeech.setText(this.dataPractice.inputSpeech)
        this.textOutputSpeech.setText("")
    }

    fun configRecognitionSpeech() {
        setIntenRecognitonSpeech()
    }

    fun startPracticeTest() {
        if (this.speech != null) {
            stopPracticeTest()
        }
        startServiceRecognition()
        Log.i(TAG, "startPracticeTest  start");
    }

    fun stopPracticeTest() {
        this.stopServiceRecognition()
        Log.i(TAG, "stopPracticeTest  start");
    }

    private fun startServiceRecognition() {
        if (this.speech != null) {
            speech?.startListening(recognizerIntent)

            Log.i(TAG, "startServiceRecognition  destroy");
        }
    }

    fun destroyServiceRecognition() {
        if (this.speech != null) {
            this.speech?.destroy()

            Log.i(TAG, "destroyServiceRecognition  destroy");
        }
    }

    private fun stopServiceRecognition() {
        if (this.speech != null) {
            this.speech?.stopListening()

            Log.i(TAG, "stopRecognitionListening  stop");
        }
    }


    fun integratedCountDownTimer(time: Long) {
        val COUNT_DOWN_INTERVAL = 500L

        var a = object : CountDownTimer(time, COUNT_DOWN_INTERVAL) {
            var curentTime = time

            override fun onTick(millisUntilFinished: Long) {
                curentTime = curentTime - COUNT_DOWN_INTERVAL
            }

            override fun onFinish() {

            }
        }.start()
    }


    private var speech: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    private fun setIntenRecognitonSpeech() {
        this.speech = SpeechRecognizer.createSpeechRecognizer(this.context)
        Log.i(TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this.context))
        this.speech?.setRecognitionListener(this)
        this.recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        this.recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en")
        this.recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        this.recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
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

    //
    override fun onReadyForSpeech(params: Bundle?) {
        Log.i(TAG, "onReadyForSpeech after setIntenRecognitonSpeech");
        // NTT TODO record service start...
        this.integratedCountDownTimer(((this.dataPractice.time - 3) * 1000).toLong())
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
        Log.e(TAG, "onError runtime, not error code FAILED ::: $errorMessage")

        // TODO NTT case, current not handle
        if (error == SpeechRecognizer.ERROR_NO_MATCH) {
            Thread.sleep(100)
            this.destroyServiceRecognition()
            this.configRecognitionSpeech()
            this.startPracticeTest()
            // \TODO NTT case, current not handle

        }
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")
        val matches = RecognitionUtils.getArrayStringResult(results);
        Log.i(TAG, "onResults :::: " + matches)
        this.textOutputSpeech.setText(matches.toString())

        this.stopServiceRecognition()
        Thread.sleep(500)
        timerCallback.updateNextPracticeViewCallback()
    }

    // \
}