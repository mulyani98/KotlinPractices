package com.mououoo.kotlinpractices.jetpackcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FaceRetouchingNatural
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HaloCompose() {
    println("Call Screen")
    Text("Hallo My Coffee")
}

@Preview(showBackground = true)
@Composable
fun HaloComposePreview() {
    HaloCompose()
}

@Preview(showBackground = true)
@Composable
fun ModifierOrderA() {
    Text (
        text = "My Coffee",
        modifier = Modifier
            .background(Color.Yellow)
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ModifierOrderB() {
    Text (
        text = "My Coffee",
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Yellow)
    )
}

@Composable
fun RecompositionDemo() {
    println("RecompositionDemo called")

    var count by remember {
        mutableIntStateOf(0)
    }

    Column {
        Text("Total = $count")

        Button(
            onClick = {
                count++
            }
        ) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageDemo() {
    Image (
        painter = painterResource(android.R.drawable.ic_menu_agenda),
        contentDescription = "Image"
    )
}

@Preview(showBackground = true)
@Composable
fun IconDemo() {
    Icon (
        imageVector = Icons.Default.FaceRetouchingNatural,
        contentDescription = "FaceRetouchingNatural",
        tint = Color.Blue
    )
}
