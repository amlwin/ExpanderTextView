package com.banner.expander

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.content.ContextCompat

class ExpanderTextView(context: Context, attributeSet: AttributeSet) :
    TextView(context, attributeSet) {

    private val TAG = ExpanderTextView::class.java.simpleName

    private val INVALID_LINE_INDEX = -1
    //3 line start from zero
    private val DEFAULT_TRIM_LINE = 2
    private val ELLIPSIZE = "..."
    private val DEFAULT_CONTINUE_TEXT = "Read More"

    private lateinit var bufferType: BufferType
    private lateinit var originalText: CharSequence

    private var lineIndexEnd: Int = INVALID_LINE_INDEX

    private var showCollapse = true

    init {
        attachTree()
        attachListener()
    }

    private fun attachListener() {
        this.setOnClickListener {
            if (showCollapse) {
                //show Collapse txt
                showCollapse = !showCollapse
                Log.d(TAG, "show collapse ")
                setText()
            } else {
                //show Expand Text
                showCollapse = !showCollapse
                Log.d(TAG, "show extend ")
                setText()
            }
        }
    }

    private fun attachTree() {
        this.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)

                if (this@ExpanderTextView.lineCount > DEFAULT_TRIM_LINE) {
                    lineIndexEnd = this@ExpanderTextView.layout.getLineEnd(DEFAULT_TRIM_LINE)
                    setText()
                }


            }

        })
    }

//    Start of a string = ^ ,
//    End of a string = $ ,
//    regex combination = | ,
//    Linebreak = \r\n|[\n\x0B\x0C\r\u0085\u2028\u2029]

    override fun setText(text: CharSequence?, type: BufferType?) {
        this.originalText = text?.replace(
            Regex("\n"),
            ""
        ) as CharSequence
        Log.d(TAG, "original text $originalText")
        this.bufferType = type!!
        setText()
    }

    private fun setText() {
        super.setText(craftText(), bufferType)
        movementMethod = LinkMovementMethod.getInstance()
    }

    private fun craftText(): CharSequence {
        return if (showCollapse) {
            //show trim text
            prepareTrimText()

        } else {
            //show original text
            prepareOriginalText()

        }
    }

    private fun prepareOriginalText(): CharSequence {
        return originalText
    }

    private fun prepareTrimText(): CharSequence {
        if (lineIndexEnd != INVALID_LINE_INDEX) {
            val trimTextIndex = lineIndexEnd - (ELLIPSIZE.length + DEFAULT_CONTINUE_TEXT.length)
            Log.d(TAG, "size is ${originalText.length} and text is $originalText")
            val trimText = SpannableStringBuilder(originalText, 0, trimTextIndex)
                .append(ELLIPSIZE)
                .append(DEFAULT_CONTINUE_TEXT)

            //color the Continue Text
            trimText.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.read_more_blue)),
                trimText.length - DEFAULT_CONTINUE_TEXT.length,
                trimText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return trimText
        }
        return originalText
    }
}