package com.mououoo.kotlinpractices

import android.util.Log

object SafeLogKotlin {
    private const val TAG = "Kotlin Practices App"
    private const val IS_DEBUG = true

    fun v() {
        if (IS_DEBUG) {
            Log.v(TAG, getMetaInfo())
        }
    }

    fun v(message: String?) {
        if (IS_DEBUG) {
            Log.v(TAG, getMetaInfo() + null2str(message))
        }
    }

    fun v(tag: String, message: String?) {
        if (IS_DEBUG) {
            Log.v(tag, getMetaInfo() + null2str(message))
        }
    }

    fun d() {
        if (IS_DEBUG) {
            Log.d(TAG, getMetaInfo())
        }
    }

    fun d(message: String?) {
        if (IS_DEBUG) {
            Log.d(TAG, getMetaInfo() + null2str(message))
        }
    }

    fun d(tag: String, message: String?) {
        if (IS_DEBUG) {
            Log.d(tag, getMetaInfo() + null2str(message))
        }
    }

    fun i() {
        if (IS_DEBUG) {
            Log.i(TAG, getMetaInfo())
        }
    }

    fun i(message: String?) {
        if (IS_DEBUG) {
            Log.i(TAG, getMetaInfo() + null2str(message))
        }
    }

    fun i(tag: String, message: String?) {
        if (IS_DEBUG) {
            Log.i(tag, getMetaInfo() + null2str(message))
        }
    }

    fun w(message: String?) {
        if (IS_DEBUG) {
            Log.w(TAG, getMetaInfo() + null2str(message))
        }
    }

    fun w(tag: String, message: String?) {
        if (IS_DEBUG) {
            Log.w(tag, getMetaInfo() + null2str(message))
        }
    }

    fun w(message: String?, e: Throwable) {
        if (IS_DEBUG) {
            Log.w(TAG, getMetaInfo() + null2str(message), e)
            printThrowable(e)
            e.cause?.let { printThrowable(it) }
        }
    }

    fun w(tag: String, message: String?, e: Throwable) {
        if (IS_DEBUG) {
            Log.w(tag, getMetaInfo() + null2str(message), e)
            printThrowable(tag, e)
            e.cause?.let { printThrowable(it) }
        }
    }

    fun e(message: String?) {
        if (IS_DEBUG) {
            Log.e(TAG, getMetaInfo() + null2str(message))
        }
    }

    fun e(tag: String, message: String?) {
        if (IS_DEBUG) {
            Log.e(tag, getMetaInfo() + null2str(message))
        }
    }

    fun e(message: String?, e: Throwable) {
        if (IS_DEBUG) {
            Log.e(TAG, getMetaInfo() + null2str(message), e)
            printThrowable(e)
            e.cause?.let { printThrowable(it) }
        }
    }

    fun e(tag: String, message: String?, e: Throwable) {
        if (IS_DEBUG) {
            Log.e(tag, getMetaInfo() + null2str(message), e)
            printThrowable(tag, e)
            e.cause?.let { printThrowable(it) }
        }
    }

    fun e(e: Throwable) {
        if (IS_DEBUG) {
            printThrowable(e)
            e.cause?.let { printThrowable(it) }
        }
    }

    private fun null2str(string: String?): String {
        return string ?: "(null)"
    }

    private fun printThrowable(e: Throwable) {
        Log.e(TAG, "${e.javaClass.name}: ${e.message}")
        e.stackTrace.forEach { element ->
            Log.e(TAG, "  at ${getMetaInfo(element)}")
        }
    }

    private fun printThrowable(tag: String, e: Throwable) {
        Log.e(tag, "${e.javaClass.name}: ${e.message}")
        e.stackTrace.forEach { element ->
            Log.e(TAG, "  at ${getMetaInfo(element)}")
        }
    }

    private fun getMetaInfo(): String {
        val element = Thread.currentThread().stackTrace[4]
        return getMetaInfo(element)
    }

    private fun getMetaInfo(element: StackTraceElement): String {
        val fileName = element.fileName
        val methodName = element.methodName
        val lineNumber = element.lineNumber
        return "($fileName:$lineNumber) [#$methodName]"
    }
}