package com.github.kylichist.aavmv.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.github.kylichist.aavmv.R
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appbar: AppBarLayout = findViewById(R.id.appbar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        appbar.apply {
            elevation = 0f
            outlineProvider = null
        }
        toolbar.apply {
            title = "Token"

        }
    }
}