package com.github.kylichist.aavmv.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.github.kylichist.aavmv.R
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

fun textOrGone(textView: TextView, text: String) {
    if (text.isNotBlank()) textView.text = text
    else textView.visibility = View.GONE
}

fun showDialog(
    context: Context, text: String = "",
    also: String = "", onCancel: () -> Unit = {}
) {
    val dialogLayout = View.inflate(
        context,
        R.layout.dialog,
        null
    ) as LinearLayout
    val defaultTextView: TextView = dialogLayout.findViewById(R.id.dialog_text)
    val optTextView: TextView = dialogLayout.findViewById(R.id.dialog_text_also)
    textOrGone(defaultTextView, text)
    textOrGone(optTextView, also)

    val button: Button = dialogLayout.findViewById(R.id.dialog_confirm)
    val dialog = AlertDialog.Builder(context)
        .setView(dialogLayout)
        .setCancelable(false)
        .create()
    button.setOnClickListener {
        dialog.cancel()
        onCancel()
    }
    dialog.apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
    }
}

fun get(url: String): JSONObject = URL(url)
    .openConnection()
    .asHttpURLConnection()
    .inputStream
    .bufferedReader()
    .readText()
    .toJSONObject()

fun URLConnection.asHttpURLConnection() = this as HttpURLConnection
fun String.toJSONObject() = JSONObject(this)

fun formVkRequestLink(base: String, userToken: String): String =
    base + ACCESS_TOKEN + userToken + VERSION

fun formGetUserLink(id: String, userToken: String) =
    formVkRequestLink(GET_PROFILE_INFO_LINK + id, userToken)

fun String.isValid(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun String.startIndex(from: String) = this.indexOf(from) + from.length
fun String.between(from: String, to: String) =
    with(this) { substring(startIndex(from), indexOf(to)) }

fun String.from(from: String) = with(this) { substring(startIndex(from)) }