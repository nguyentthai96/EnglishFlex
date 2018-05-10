package com.ntt.groupfour.englishflex.feature.customview

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import com.weiwangcn.betterspinner.library.R
import java.util.*

class AutoCompleteSpinner : AutoCompleteTextView, AdapterView.OnItemClickListener {


    private val MAX_CLICK_DURATION = 200
    private var startClickTime: Long = 0
    private var isPopup: Boolean = false

    constructor(context: Context?) : super(context) {
        onItemClickListener = this
        this.threshold = 1
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        onItemClickListener = this
        this.threshold = 1
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        onItemClickListener = this
        this.threshold = 1
    }

    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            try {
                performFiltering("", 0)
            } catch (e: Exception) {
                Log.e("AutoCompleteSpi", "Error catch $e")
            }
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
            keyListener = null
            dismissDropDown()
        } else {
            isPopup = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = Calendar.getInstance().timeInMillis
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if (clickDuration < MAX_CLICK_DURATION) {
                    if (isPopup) {
                        dismissDropDown()
                        isPopup = false
                    } else {
                        requestFocus()
                        showDropDown()
                        isPopup = true
                    }
                }
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        isPopup = false
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
        var right = right
        val dropdownIcon = ContextCompat.getDrawable(context, R.drawable.ic_expand_more_black_18dp)
        if (dropdownIcon != null) {
            right = dropdownIcon
            right.mutate().alpha = 128
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

}