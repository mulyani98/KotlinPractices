package com.mououoo.kotlinpractices.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.mououoo.featurecalculator.ui.CalculatorActivity
import com.mououoo.kotlinpractices.R
import com.mououoo.kotlinpractices.activity.SimpleCoroutineActivity
import com.mououoo.kotlinpractices.activity.CoroutineActivity
import com.mououoo.kotlinpractices.activity.DispatcherTypeActivity
import com.mououoo.kotlinpractices.activity.KotlinNotesActivity
import com.mououoo.kotlinpractices.activity.TaskOneActivity
import com.mououoo.kotlinpractices.activity.TaskTwoActivity
import com.mououoo.kotlinpractices.activity.ThreadAndExecutorActivity
import com.mououoo.kotlinpractices.jetpackcompose.JetpackComposeMain
import com.mououoo.kotlinpractices.trial.employee.EmployeeActivity
import com.mououoo.kotlinpractices.trial.user.UserActivity

class MenuHomeViewModel : ViewModel() {
    fun getMenuItems(context: Context): List<MenuItemData> {
        return listOf(
            MenuItemData(
                title = context.getString(R.string.go_to_task_one),
                onClick = {
                    context.startActivity(Intent(context, TaskOneActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.go_to_task_two),
                onClick = {
                    context.startActivity(Intent(context, TaskTwoActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.go_to_dispatcher_type),
                onClick = {
                    context.startActivity(Intent(context, DispatcherTypeActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.kotlin_notes_title),
                onClick = {
                    context.startActivity(Intent(context, KotlinNotesActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.coroutine_title),
                onClick = {
                    context.startActivity(Intent(context, CoroutineActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.simple_coroutine_title),
                onClick = {
                    context.startActivity(Intent(context, SimpleCoroutineActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.thread_and_executor_title),
                onClick = {
                    context.startActivity(Intent(context, ThreadAndExecutorActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.calc_title),
                onClick = {
                    context.startActivity(Intent(context, CalculatorActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.coroutine_user_title),
                onClick = {
                    context.startActivity(Intent(context, UserActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.coroutine_employee_title),
                onClick = {
                    context.startActivity(Intent(context, EmployeeActivity::class.java))
                }
            ),
            MenuItemData(
                title = context.getString(R.string.jetpack_compose_title),
                onClick = {
                    context.startActivity(Intent(context, JetpackComposeMain::class.java))
                }
            )
        )
    }
}

data class MenuItemData(val title: String, val onClick: () -> Unit)


