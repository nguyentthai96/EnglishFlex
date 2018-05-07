package com.ntt.groupfour.englishflex.feature.customview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ntt.groupfour.englishflex.feature.R
import com.ntt.groupfour.englishflex.feature.model.RecognitionSpeech
import com.ntt.groupfour.englishflex.feature.model.SpeechData
import com.ntt.groupfour.englishflex.feature.utils.AppConstants

class PracticeTestFragment : Fragment() {

    val TAG="PracticeTF"
    private lateinit var textInput: TextView
    private lateinit var textResult: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.view_text_recognition_layout, container, false)
        this.textInput = view!!.findViewById<TextView>(R.id.textInput)
        this.textResult = view.findViewById<TextView>(R.id.textResult)

        val args = arguments
        this.textInput.text = args?.getString(AppConstants.TEXT_SAMPLE)


        return view;
    }

    fun callBackSetRecognitionResult(text: String) {
        this.textResult.setText(text)
    }

    companion object {
        val TAG="PracticeTF"
        // Method for creating new instances of the fragment
        fun newInstance(recognitionSpeech: SpeechData): PracticeTestFragment {

            Log.i(TAG,"fun newInstance(recognitionSpeech: SpeechData): PracticeTestFragment {")
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