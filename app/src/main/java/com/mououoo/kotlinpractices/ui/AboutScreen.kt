package com.mououoo.kotlinpractices.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Intent
import androidx.compose.ui.res.stringResource
import com.mououoo.kotlinpractices.R
import com.mououoo.kotlinpractices.WebViewActivity

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            MenuItem(stringResource(R.string.home_page)) {
                val intent = Intent(context, WebViewActivity::class.java).apply {
                    // Pass the desired URL
                    putExtra("url", "https://mulyani98.github.io/Home-page-Kotlin-Practices/")
                }
                context.startActivity(intent)
            }

            MenuItem(stringResource(R.string.term_of_use)) {
                val intent = Intent(context, WebViewActivity::class.java).apply {
                    // Pass the desired URL
                    putExtra("url", "https://mulyani98.github.io/ToU-Kotlin-Practices/")
                }
                context.startActivity(intent)
            }

            MenuItem(stringResource(R.string.privacy_statement)) {
                val intent = Intent(context, WebViewActivity::class.java).apply {
                    // Pass the desired URL
                    putExtra("url", "https://mulyani98.github.io/PSA-Kotlin-Practices/")
                }
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun MenuItem(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, style = MaterialTheme.typography.body1)
            Icon(Icons.Filled.ArrowForward, contentDescription = "Go")
        }
    }
}
