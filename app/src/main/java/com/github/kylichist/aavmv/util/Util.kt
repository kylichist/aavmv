package com.github.kylichist.aavmv.util

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.github.kylichist.aavmv.R
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
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

fun String.isValid(): Boolean {
    try {
        @Suppress("UNUSED_VARIABLE")
        val check = URL(this)
    } catch (fail: MalformedURLException) {
        return false
    }
    return true
}