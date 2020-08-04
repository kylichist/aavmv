package com.github.kylichist.aavmv.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.Annotation
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
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

        val tokenEditText: EditText = findViewById(R.id.token_url)
        val tokenConfirm: Button = findViewById(R.id.confirm)
        tokenConfirm.setOnClickListener {
            val link = tokenEditText.text.toString()
            if (link.isNotEmpty()) {
                if (link.isValid()) {
                    if (link.contains("token=") && link.contains("user_id=")) {
                        val token =
                            link.substring(link.indexOf("token=") + 6, link.indexOf("&expires"))
                        val id = link.substring(link.indexOf("user_id=") + 8)
                        TODO()
                        Log.d("TAG", get(GET_PROFILE_INFO_LINK_START + id + GET_PROFILE_INFO_LINK_END).toString(4))
                    } else {
                        //TODO: show dialog on error
                    }
                }
            }
        }

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
    }
}