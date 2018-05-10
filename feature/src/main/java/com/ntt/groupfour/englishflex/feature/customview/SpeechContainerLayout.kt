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
import com.ntt.groupfour.englishflex.feature.callback.PracticeActionCallback
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.utils.AppConstants
import com.ntt.groupfour.englishflex.feature.utils.RecognitionUtils

class SpeechContainerLayout : FrameLayout, RecognitionListener {

    val TAG = "SpeechConL"

    private lateinit var dataPractice: SpeechData;

    private lateinit var textInputSpeech: TextView
    private lateinit var textOutputSpeech: TextView

    private lateinit var practiceActionCallback: PracticeActionCallback

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_speech_recognition_layout, this)

        //        val view= View.inflate(context, R.layout.recognition_practices_layout, (rootView as FrameLayout).findViewById(R.id.containerLayout))
        this.textInputSpeech = findViewById(R.id.textInputSpeech)
        this.textOutputSpeech = findViewById(R.id.textOutputSpeech)

        this.configRecognitionSpeech()
    }

    fun setOnTimerCallback(practiceActionCallback: PracticeActionCallback) {
        this.practiceActionCallback = practiceActionCallback
    }

    fun updateDataView(data: SpeechData) {
        this.dataPractice = data
        Log.i(TAG, dataPractice.toString())
        this.textInputSpeech.setText(this.dataPractice.inputSpeech)
        this.textOutputSpeech.setText("")
    }

    fun reviewDataPracticeView(data: SpeechData) {
        this.dataPractice = data
        Log.i(TAG, dataPractice.toString())
        this.textInputSpeech.setText(this.dataPractice.inputSpeech)
        this.textOutputSpeech.setText(data.getBestResult()?.textRecognition)
    }

    fun configRecognitionSpeech() {
        setIntenRecognitonSpeech()
    }

    // check stop service and start
    fun startPracticeTest() {
        if (this.speech != null) {
            stopPracticeTest()
        }
        startServiceRecognition()
        this.textOutputSpeech.text = null
        this.practiceActionCallback.startTimerCount((dataPractice.time - AppConstants.TIME_COUNTDOWN) * 1000L)
        Log.i(TAG, "startPracticeTest  start");
    }

    fun restartPracticeTest() {
        // NTT TODO no code
        if (this.speech != null) {
            stopPracticeTest()
        }
        startServiceRecognition()
        Log.i(TAG, "restartPracticeTest  restart");
    }


    fun stopPracticeTest() {
        this.stopServiceRecognition()
        Log.i(TAG, "stopPracticeTest  start");
    }

    // check null speech service and start
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
        practiceActionCallback.showProgressBarRecognition()
        // NTT TODO record service start...
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
        practiceActionCallback.showMicRecognition()
        // TODO NTT case, current not handle
        if (error == SpeechRecognizer.ERROR_NO_MATCH) {
            this.stopServiceRecognition()
            this.configRecognitionSpeech()
            this.restartPracticeTest()
            // \TODO NTT case, current not handle

        }
    }

    override fun onResults(results: Bundle?) {
        val matches = RecognitionUtils.getArrayStringResult(results);
        Log.i(TAG, "onResults ::: ${matches.toString()}")

        this.stopServiceRecognition()

        if (matches != null) {
            val bestResult = dataPractice.addRecognizeResponseBest(matches)
            this.textOutputSpeech.text = bestResult

            if (bestResult.toUpperCase().equals(dataPractice.inputSpeech.toUpperCase())) {
                correctedText(bestResult)
            }
        } else {
            this.startServiceRecognition()
        }
    }

    // \

    fun correctedText(results: String) {
        val TIME_DELAY_VIEW_RESULT = 500L

        object : CountDownTimer(TIME_DELAY_VIEW_RESULT, TIME_DELAY_VIEW_RESULT) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                practiceActionCallback.updateNextPracticeViewCallback()
            }
        }.start()
    }
}