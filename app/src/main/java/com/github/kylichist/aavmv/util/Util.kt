package com.github.kylichist.aavmv.util

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.github.kylichist.aavmv.R
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun toDP(px: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, Resources.getSystem().displayMetrics)

fun View.bind() {
    this.background = GradientDrawable().apply {
        setColor(ContextCompat.getColor(context, R.color.colorAccent))
        cornerRadius = toDP(15f)
    }
}

fun CardView.bind() {
    this.apply {
        radius = toDP(15f)
        cardElevation = 5f
    }
}

fun get(url: String): JSONObject {
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.apply {
        requestMethod = "GET"
        doOutput = true
        connect()
    }
    val scanner = Scanner(connection.inputStream)
    var out = ""
    while (scanner.hasNextLine()) out += scanner.nextLine()
    return JSONObject(out)
}

fun String.isValid(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun String.startIndex(from: String) = this.indexOf(from) + from.length
fun String.between(from: String, to: String) =
    this.substring(this.startIndex(from), this.indexOf(to))

fun String.from(from: String) = this.substring(this.startIndex(from))