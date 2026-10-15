package com.mououoo.kotlinpractices.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mououoo.kotlinpractices.ui.AboutScreen
import com.mououoo.kotlinpractices.ui.theme.MyAppTheme

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyAppTheme {
                AboutScreen()
            }
        }
    }
}