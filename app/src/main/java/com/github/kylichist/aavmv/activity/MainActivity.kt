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
import com.github.kylichist.aavmv.vk.getName
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            with(tokenEditText.text.toString()) {
                if (isValid() &&
                    contains("https://oauth.vk.com/blank.html#access_token=")
                ) {
                    val id = from("user_id=")
                    val userToken = between("token=", "&expires")
                    GlobalScope.launch(Main) {
                        val name = withContext(IO) { getName(id, userToken) }
                        sharedPreferences.edit()
                            .putString("token", userToken)
                            .apply()
                        if (name !== null)
                            showDialog(
                                this@MainActivity,
                                getString(R.string.logged_as),
                                name
                            ) {
                                //change to def layout
                            }
                        else showErrorDialog()
                    }
                } else showErrorDialog()
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
                    data = Uri.parse(GET_TOKEN_LINK_DEBUG + VERSION)
                }.also { startActivity(it) }
            }
        }
        annotations.find { it.value == "link" }
            .also {
                tokenDescriptionSpannable.apply {
                    with(tokenDescriptionText) {
                        setSpan(
                            clickableSpan,
                            getSpanStart(it),
                            getSpanEnd(it),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(this@MainActivity, R.color.colorLink)
                            ),
                            getSpanStart(it),
                            getSpanEnd(it),
                            0
                        )
                    }
                }
            }
        tokenDescriptionTextView.apply {
            text = tokenDescriptionSpannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun loadTokenInputState() {}

    private fun loadDefaultState() {}

    private fun showErrorDialog() = showDialog(
        context = this,
        also = getString(R.string.error)
    )
}