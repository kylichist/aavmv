package com.github.kylichist.aavmv.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.Annotation
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.github.kylichist.aavmv.R
import com.github.kylichist.aavmv.util.*
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appbar: AppBarLayout = findViewById(R.id.appbar)
        appbar.apply {
            elevation = 0f
            outlineProvider = null
        }
        val sharedPreferences = EncryptedSharedPreferences.create(
            this,
            SHARED_PREFERENCES_NAME,
            MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val token = sharedPreferences.getString("token", "no")
        if (token == "no") {

        }

        val circle1: LinearLayout = findViewById(R.id.circle1)
        circle1.bind()

        val circle2: LinearLayout = findViewById(R.id.circle2)
        circle2.bind()

        val line: LinearLayout = findViewById(R.id.line)
        line.bind()

        val tokenEditLinear: LinearLayout = findViewById(R.id.token_edit_background)
        tokenEditLinear.bindWithColor(R.color.colorTokenEditBackground)

        val addTokenCard: CardView = findViewById(R.id.card_add_token)
        addTokenCard.bind()

        val tokenDescriptionTextView: TextView = findViewById(R.id.token_description)
        val tokenDescriptionText = getText(R.string.token_description) as SpannedString
        val tokenDescriptionSpannable = SpannableString(tokenDescriptionText)
        val annotations = tokenDescriptionText.getSpans(
            0,
            tokenDescriptionText.length,
            Annotation::class.java
        )
        //TODO DON'T FORGET TO CHANGE LINK
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(GET_TOKEN_LINK_DEBUG)
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
        tokenDescriptionTextView.apply {
            text = tokenDescriptionSpannable
            movementMethod = LinkMovementMethod.getInstance()
        }

        val tokenEditText: EditText = findViewById(R.id.token_url)
        tokenEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isValid(tokenEditText.text.toString())) {

                }
            }
        })
    }
}