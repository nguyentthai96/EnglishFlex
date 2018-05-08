package com.ntt.groupfour.englishflex.feature.customview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.utils.AppConstants

class PracticeTestFragment : Fragment() {

    val TAG = "PracticeTF"
    protected lateinit var textInput: TextView
    protected lateinit var textResult: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.view_speech_recognition_layout, container, false)
        this.textInput = view!!.findViewById<TextView>(R.id.textInputSpeech)
        this.textResult = view.findViewById<TextView>(R.id.textOutputSpeech)

        val args = arguments
        this.textInput.text = args?.getString(AppConstants.TEXT_SAMPLE)

        Log.i(TAG, "override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)")

        return view;
    }

    fun updateText(){
        this.textResult.setText("sdsdfg")
        Log.i(TAG, "fun updateText(){")
    }

    companion object {
        val TAG = "PracticeTF"
        // Method for creating new instances of the fragment
        fun newInstance(recognitionSpeech: SpeechData): PracticeTestFragment {
            Log.i(TAG, "fun newInstance(recognitionSpeech: SpeechData): PracticeTestFragment")

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putString(AppConstants.TEXT_SAMPLE, recognitionSpeech.inputSpeech)


            // Create a new MovieFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = PracticeTestFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
