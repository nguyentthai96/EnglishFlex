package com.ntt.groupfour.englishflex.feature.customview

import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ntt.groupfour.englishflex.feature.R


class DialogCustoms : DialogFragment() {

    val TAG = "DialogCustom"

    interface OnCallBack {
        fun onClickButtonChooseFile()
    }

    private lateinit var onCallBack: OnCallBack

    fun setOnCallBack(onCallBack: OnCallBack) {
        this.onCallBack = onCallBack
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    companion object {
        fun newInstance(num: Int): DialogCustoms {
            val f = DialogCustoms()

            // Supply num input as an argument.
            val args = Bundle()
            args.putInt("num", num)
            f.setArguments(args)

            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mNum = 2

        // Pick a style based on the num.
        var style = DialogFragment.STYLE_NORMAL
        var theme = 0
        when ((mNum - 1) % 6) {
            1 -> style = DialogFragment.STYLE_NO_TITLE
            2 -> style = DialogFragment.STYLE_NO_FRAME
            3 -> style = DialogFragment.STYLE_NO_INPUT
            4 -> style = DialogFragment.STYLE_NORMAL
            5 -> style = DialogFragment.STYLE_NORMAL
            6 -> style = DialogFragment.STYLE_NO_TITLE
            7 -> style = DialogFragment.STYLE_NO_FRAME
            8 -> style = DialogFragment.STYLE_NORMAL
        }
        when ((mNum - 1) % 6) {
            4 -> theme = android.R.style.Theme_Holo
            5 -> theme = android.R.style.Theme_Holo_Light_Dialog
            6 -> theme = android.R.style.Theme_Holo_Light
            7 -> theme = android.R.style.Theme_Holo_Light_Panel
            8 -> theme = android.R.style.Theme_Holo_Light
        }

        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialog, container, false)
        val title: TextView = view.findViewById(R.id.title)
        title.text = "Import data practice"

        val linkSample: TextView = view.findViewById(R.id.linkSample)
        val text = "Download sample data here."
        val url = "https://drive.google.com/uc?authuser=0&id=1zZADMVqiEuZHZk24vE7Wz9GY7TtTcRdq&export=download"
        val link = fromHtml("<a href='$url'>$text</a>")
        Linkify.addLinks(linkSample, Linkify.WEB_URLS);
        linkSample.setMovementMethod(LinkMovementMethod.getInstance());
        linkSample.text = link

        view.findViewById<ImageView>(R.id.closeDialog).setOnClickListener(View.OnClickListener {
            this.dismiss()
        })

        val buttonOpenChooseFile: Button = view.findViewById(R.id.buttonBrowseOpenFile)
        buttonOpenChooseFile.setOnClickListener(View.OnClickListener {
            onCallBack.onClickButtonChooseFile()
        })

        return view
    }

    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    override fun onStart() {
        super.onStart()
        val width = resources.getDimensionPixelSize(R.dimen.popup_width)
        val height = resources.getDimensionPixelSize(R.dimen.popup_height)
        dialog.window!!.setLayout(width, height)
    }
}