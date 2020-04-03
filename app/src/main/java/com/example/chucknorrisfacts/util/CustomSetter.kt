package com.example.chucknorrisfacts.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.customMessage(): String{
    val notFoundError = "Request not found. Try to update your input."
    val timeOutError = "Failed to connect to service. Verify your internet connection and retry again."
    val connectionError = "Internet connection is not available."
    val unknownError =  "Unexpected error."

    return when(this){
        is HttpException -> when(code()) {
            404 -> notFoundError
            else -> unknownError
        }
        is SocketTimeoutException -> timeOutError
        is UnknownHostException ->
            if(!verifyInternetConnection()) connectionError
            else unknownError
        else -> unknownError
    }
}

fun verifyInternetConnection(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        return exitValue == 0
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    return false
}

fun Activity.hideKeyboard() {
    val inputManager: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun View.showIfTrue(status: Boolean){
    visibility = if(status) View.VISIBLE else View.GONE
}