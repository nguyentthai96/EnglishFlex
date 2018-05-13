package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.callback.LayoutCallback
import com.ntt.groupfour.englishflex.feature.callback.PracticeActionCallback
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.service.DataHelper
import com.ntt.groupfour.englishflex.feature.utils.AppConstants


class PracticeRecognitionLayout : FrameLayout, PracticeActionCallback {

    val TAG = "PracticeRecoLL"

    private var indexPracticeCurrent = 0
    private var currentScore = 0
    var standarScore: Int = 0
    private lateinit var dataPractices: ArrayList<SpeechData>

    private var speechRecognitonContainer: SpeechContainerLayout
    private var inPracticeLayout: LinearLayout
    private var outPracticeLayout: LinearLayout
    private var settingContainer: SettingContainerLayout
    private var progressBar: ProgressBar
    private var buttonMicRestart: ImageView
    private var buttonStop: ImageView
    private var textCountdownClock: TextView
    private var nextTest: TextView
    private var prevTest: TextView
    private var scoreNumber: TextView
    private var buttonNewStart: ImageView
    private val handlerTimer = Handler()
    private lateinit var runnable: Runnable

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.recognition_practices_layout, this)
        this.speechRecognitonContainer = findViewById(R.id.speechRecognitonContainer)
        this.settingContainer = findViewById(R.id.settingContainer)
        this.progressBar = findViewById(R.id.progressBar)
        this.buttonMicRestart = findViewById(R.id.buttonMic)
        this.buttonStop = findViewById(R.id.buttonStop)
        this.textCountdownClock = findViewById(R.id.textCountdownClock)
        this.nextTest = findViewById(R.id.nextTest)
        this.prevTest = findViewById(R.id.prevTest)
        this.scoreNumber = findViewById(R.id.scoreNumber)

        this.speechRecognitonContainer.setOnTimerCallback(this)

        this.buttonMicRestart.setOnClickListener(OnClickListener {
            this.speechRecognitonContainer.restartPracticeTest()
        })

        this.buttonStop.setOnClickListener(OnClickListener { this.finishPractice() })
        this.prevTest.setOnClickListener(OnClickListener {
            prevTestClick()
        })
        this.nextTest.setOnClickListener(OnClickListener {
            this.nextTestClick()
        })

        this.inPracticeLayout = findViewById(R.id.inPracticeLayout)
        this.inPracticeLayout.visibility = View.VISIBLE
        this.outPracticeLayout = findViewById(R.id.outPracticeLayout)
        this.buttonNewStart = findViewById(R.id.buttonNewStart)
        this.buttonNewStart.setOnClickListener(View.OnClickListener { view ->
            this.progressBar.visibility = View.VISIBLE

            this.prevTest.visibility = View.INVISIBLE
            this.nextTest.visibility = View.INVISIBLE
            this.outPracticeLayout.visibility = View.GONE
            this.inPracticeLayout.visibility = View.VISIBLE

            onStartCallback?.onStartCallback()
        })
    }

    private lateinit var onStartCallback: LayoutCallback
    fun setOnStartCallback(onStartCallback: LayoutCallback) {
        this.onStartCallback = onStartCallback
    }

    fun reshowSettingView() {
        this.settingContainer.showCurrentSettingView()
    }

    private fun setViewPrevNextPractice() {
        this.prevTest.visibility = View.INVISIBLE
        if (this.indexPracticeCurrent > 0) {
            this.prevTest.visibility = View.VISIBLE
        }

        this.nextTest.visibility = View.VISIBLE
        if (this.indexPracticeCurrent == this.dataPractices.size - 1) {
            this.nextTest.visibility = View.INVISIBLE
        }
    }

    private fun nextTestClick() {
        var data = this.getNextPractice()

        try {
            this.speechRecognitonContainer.reviewDataPracticeView(data!!)
        } catch (nullEx: KotlinNullPointerException) {
            Log.e(TAG, "Error catch :: $nullEx")
        }
        this.setViewPrevNextPractice()
    }

    private fun prevTestClick() {
        var data = this.getPrevPractice()

        try {
            this.speechRecognitonContainer.reviewDataPracticeView(data!!)
        } catch (nullEx: KotlinNullPointerException) {
            Log.e(TAG, "Error catch :: $nullEx")
        }
        this.setViewPrevNextPractice()
    }

    fun createViewUIAndConfig() {
//        val view= View.inflate(context, R.layout.recognition_practices_layout, (rootView as FrameLayout).findViewById(R.id.containerLayout))
        this.dataPractices = DataHelper().getSpeechData(); // get from singleton
        this.standarScore = DataHelper().getStandarScore(this.dataPractices)

        // config data
        this.indexPracticeCurrent = 0
        this.speechRecognitonContainer.updateDataView(this.dataPractices[indexPracticeCurrent])
        this.speechRecognitonContainer.configRecognitionSpeech()
        this.speechRecognitonContainer.startPracticeTest()
    }

    override fun startTimerCount(time: Long) {
        Log.d(TAG, "Time count :: $time")

        runnable = object : Runnable {
            override fun run() {
                try {
                    Log.d(TAG, "Time count inside :: call countTimeOut $time")
                    this@PracticeRecognitionLayout.buttonMicRestart.visibility = View.INVISIBLE
                    countTimeOut()
                    Log.d(TAG, "Time count inside :: called countTimeOut ")

                } catch (e: Exception) {
                    Log.d(TAG, "Error catch exception :: $e")
                }
            }
        }

        handlerTimer.postDelayed(runnable, time)
        Log.d(TAG, "Time count  :: start startTimerCount listening")
    }

    fun countTimeOut() {
        val COUNT_DOWN_INTERVAL = 1000L

        object : CountDownTimer(AppConstants.TIME_COUNTDOWN * COUNT_DOWN_INTERVAL, COUNT_DOWN_INTERVAL) {

            var curentTime = AppConstants.TIME_COUNTDOWN

            override fun onTick(millisUntilFinished: Long) {
                textCountdownClock.visibility = View.VISIBLE
                textCountdownClock.text = "" + curentTime
                curentTime--
                this@PracticeRecognitionLayout.buttonMicRestart.visibility = View.INVISIBLE
            }

            override fun onFinish() {
                textCountdownClock.visibility = View.GONE
                curentTime = AppConstants.TIME_COUNTDOWN
                updateNextPracticeViewCallback()
                this.cancel()
            }
        }.start()
    }

    override fun updateNextPracticeViewCallback() {
        Log.d(TAG, "Time count updateNextPracticeViewCallback :: ")
        this.currentScore += this.dataPractices[indexPracticeCurrent].scoreCurrent
        this.scoreNumber.text = "${this.currentScore.toString()} / ${this.standarScore}"
        this.nextPracticeTestView()
    }

    override fun showMicRecognition() {
        this.buttonMicRestart.visibility = View.VISIBLE
        this.progressBar.visibility = View.INVISIBLE
    }

    override fun showProgressBarRecognition() {
        this.buttonMicRestart.visibility = View.INVISIBLE
        this.progressBar.visibility = View.VISIBLE
    }

    private fun getPrevPractice(): SpeechData? {
        if (indexPracticeCurrent > 0) {
            this.indexPracticeCurrent--;
            Log.d(TAG, " getNextPractice indexPracticeCurrent :: ${this.indexPracticeCurrent}")
            return this.dataPractices[indexPracticeCurrent]
        }
        return null
    }

    // return null if current last item
    private fun getNextPractice(): SpeechData? {
        if (indexPracticeCurrent < dataPractices.size - 1) {
            this.indexPracticeCurrent++;
            Log.d(TAG, " getNextPractice indexPracticeCurrent :: ${this.indexPracticeCurrent}")
            return this.dataPractices[indexPracticeCurrent]
        }
        return null
    }

    fun nextPracticeTestView() {
        var data = this.getNextPractice()

        try {
            this.speechRecognitonContainer.updateDataView(data!!)  // throw KotlinNullPointerException when null
        } catch (nullEx: KotlinNullPointerException) {
            Log.e(TAG, "Error catch :: $nullEx")
        }
        if (indexPracticeCurrent < this.dataPractices.size && data != null) {
            this.speechRecognitonContainer.startPracticeTest()
        } else {
            finishPractice()
        }
    }

    private fun finishPractice() {
        this.nextTest.visibility = View.VISIBLE
        this.progressBar.visibility = View.GONE
        this.buttonMicRestart.visibility = View.VISIBLE
        this.inPracticeLayout.visibility = View.GONE
        this.outPracticeLayout.visibility = View.VISIBLE

        this.indexPracticeCurrent = 0
        this.speechRecognitonContainer.reviewDataPracticeView(this.dataPractices[indexPracticeCurrent])
        this.speechRecognitonContainer.stopPracticeTest()
        this.speechRecognitonContainer.destroyServiceRecognition()
    }


    fun stopActivity() {
        if (this.speechRecognitonContainer != null) {
            this.speechRecognitonContainer.stopPracticeTest()
        }
        try {
            if (this.handlerTimer != null && null != this.runnable) {
                this.handlerTimer?.removeCallbacks(this.runnable)
            }
        } catch (e: UninitializedPropertyAccessException) {
            Log.d(TAG, "Error catch exception  :: $e")
        }

    }
}