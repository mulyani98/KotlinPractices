package com.mououoo.kotlinpractices.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mououoo.kotlinpractices.R

@Composable
fun KotlinNotesScreen() {
    val activity = LocalContext.current as? Activity

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            activity?.finish()
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_btn))
                        }
                        Text(text = stringResource(id = R.string.kotlin_notes_title))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Scrollable content
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp) // Add some padding to avoid overlapping with the button
            ) {
                item {
                    Text(
                        text = "When you want to println two array with ',' as separator: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "println(result.joinToString(\", \"))",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    )
                }

                item {
                    Text(
                        text = "Example impl retrieving values from array using if else: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "val valueA = if (i < a.size) a[i] else 0 \n" +
                                "val valueB = if (i < b.size) b[i] else 0 ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    )
                }

                item {
                    Text(
                        text = "Example impl retrieving values from array using getOrNull: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "val valueA = a.getOrNull(i) ?: 0 \n" +
                                "val valueB = b.getOrNull(i) ?: 0",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                item {
                    Text(
                        text = "Example usage of string resource using context: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Text(context.getString(R.string.increase_value))",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                item {
                    Text(
                        text = "Example usage of resource using stringResource: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Text(text = stringResource(id = R.string.increase_value))",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKotlinNotesScreen() {
    KotlinNotesScreen()
}