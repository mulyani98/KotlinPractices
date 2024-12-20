package com.mououoo.kotlinpractices.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mououoo.kotlinpractices.GoogleSignInActivity
import com.mououoo.kotlinpractices.KotlinNotesActivity
import com.mououoo.kotlinpractices.R
import com.mououoo.kotlinpractices.TaskOneActivity
import com.mououoo.kotlinpractices.TaskTwoActivity

@Composable
fun MainScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center), // Center the Column
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Center content inside the Column
        ) {
            val context = LocalContext.current

            // Button to Task One
            Button(onClick = {
                // Navigate to TaskTwoActivity
                context.startActivity(Intent(context, TaskOneActivity::class.java))
            }) {
                Text(text = stringResource(id = R.string.go_to_task_one))
            }

            // Button to Task Two
            Button(onClick = {
                // Navigate to TaskTwoActivity
                context.startActivity(Intent(context, TaskTwoActivity::class.java))
            }) {
                Text(text = stringResource(id = R.string.go_to_task_two))
            }

            // Button to Kotlin Notes
            Button(onClick = {
                // Navigate to KotlinNotesActivity
                context.startActivity(Intent(context, KotlinNotesActivity::class.java))
            }) {
                Text(text = stringResource(id = R.string.go_to_kotlin_notes))
            }

            // Button to Google Sign In
            Button(onClick = {
                // Navigate to Google Sign In Activity
                context.startActivity(Intent(context, GoogleSignInActivity::class.java))
            }) {
                Text(text = stringResource(id = R.string.go_to_google_sign_in))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}