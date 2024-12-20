package com.mououoo.kotlinpractices

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mououoo.kotlinpractices.ui.AboutScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                AboutScreen()
            }
        }
    }
}