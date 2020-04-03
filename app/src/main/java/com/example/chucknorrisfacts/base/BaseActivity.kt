package com.example.chucknorrisfacts.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity(), ObserveActivity {
    protected val codeRequest = 25
    protected val categoryResponse = "category"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeComponents()
    }

    protected fun showMessage(message: String?){
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}