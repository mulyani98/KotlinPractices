package com.mououoo.kotlinpractices.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mououoo.kotlinpractices.R

@Composable
fun KotlinNotesScreen(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                    text = stringResource(id = R.string.kotlin_notes_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, top = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            }

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

        // Back Button at the bottom
        Button(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.back_btn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKotlinNotesScreen() {
    KotlinNotesScreen(
        onBackClick = {}
    )
}