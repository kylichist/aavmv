package com.github.kylichist.aavmv.util

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.github.kylichist.aavmv.R
import java.net.MalformedURLException
import java.net.URL

fun toDP(px: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, Resources.getSystem().displayMetrics)

fun LinearLayout.bind() {
    this.background = GradientDrawable().apply {
        setColor(ContextCompat.getColor(context, R.color.colorAccent))
        cornerRadius = toDP(15f)
    }
}

fun LinearLayout.bindWithColor(color: Int) {
    this.background = GradientDrawable().apply {
        setColor(ContextCompat.getColor(context, color))
        cornerRadius = toDP(15f)
    }
}

fun CardView.bind() {
    this.apply {
        radius = toDP(15f)
        cardElevation = 5f
    }
}

fun isValid(url: String): Boolean {
    try {
        @Suppress("UNUSED_VARIABLE")
        val check = URL(url)
    } catch (fail: MalformedURLException) {
        return false
    }
    return true
}