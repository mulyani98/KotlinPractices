package com.mououoo.kotlinpractices.jetpackcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FaceRetouchingNatural
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

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

@Preview
@Composable
fun BoxDemo() {
    Box(
        modifier = Modifier
            .size(250.dp)
            .background(Color.Cyan)
    ) {
        Text(
            text = "Top Start",
            modifier = Modifier.align(Alignment.TopStart)
        )

        Text(
            text = "Center",
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "Bottom End",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
fun ExampleOfSpacerInsideRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan)
            .padding(12.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Left")

        Spacer(modifier = Modifier.height(10.dp))

        Text("Right")
    }
}

@Preview
@Composable
fun ItemVsItemsLazyColumn() {
    LazyColumn {
        item {
            Text("Header", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(15.dp))
        }

        items(count = 10) { index ->
            Text("Item $index")
        }

        item {
            Spacer(modifier = Modifier.height(15.dp))
            Text("Footer", style = MaterialTheme.typography.h6)
        }
    }
}

@Preview
@Composable
fun ConstraintExample() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Create reference
        val (title, button) = createRefs()

        // Create element and constraint
        Text(
            text = "Page Title",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(title.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }
        ) {
            Text("Click Me")
        }
    }
}
