package com.github.kylichist.aavmv.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.TextPaint
import android.text.Annotation
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.kylichist.aavmv.R
import com.github.kylichist.aavmv.util.GET_TOKEN_LINK
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appbar: AppBarLayout = findViewById(R.id.appbar)
        val tokenDescriptionTextview: TextView = findViewById(R.id.token_description)
        val tokenDescriptionText = getText(R.string.token_description) as SpannedString
        val tokenDescriptionSpannable = SpannableString(tokenDescriptionText)
        val annotations = tokenDescriptionText.getSpans(
            0,
            tokenDescriptionText.length,
            Annotation::class.java
        )
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(GET_TOKEN_LINK)
                }.also { startActivity(it) }
            }
        }
        annotations.find { it.value == "link" }
            .also {
                tokenDescriptionSpannable.apply {
                    setSpan(
                        clickableSpan,
                        tokenDescriptionText.getSpanStart(it),
                        tokenDescriptionText.getSpanEnd(it),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(this@MainActivity, R.color.colorLink)
                        ),
                        tokenDescriptionText.getSpanStart(it),
                        tokenDescriptionText.getSpanEnd(it),
                        0
                    )
                }
            }
        tokenDescriptionTextview.apply {
            text = tokenDescriptionSpannable
            movementMethod = LinkMovementMethod.getInstance()
        }
        appbar.apply {
            elevation = 0f
            outlineProvider = null
        }
    }
}