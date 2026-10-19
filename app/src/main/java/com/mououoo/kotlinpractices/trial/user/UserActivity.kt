package com.mououoo.kotlinpractices.trial.user

import UserViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: UserViewModel by viewModels {
            UserViewModelFactory(
                UserRepository(UserApi())
            )
        }

        enableEdgeToEdge()

        setContent {
            val dashboard = vm.dashboardFlow.collectAsStateWithLifecycle()
            Column {
                Spacer(Modifier.height(200.dp))

                Text(
                    text = "email: "
                )
                Text(
                    text = dashboard.value.user.email
                )
            }

        }
    }
}