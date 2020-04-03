package com.example.chucknorrisfacts.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chucknorrisfacts.util.customMessage
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(){
    protected val compositeDisposable = CompositeDisposable()

    private val mutableError = MutableLiveData<String>()
    protected val mutableLoading = MutableLiveData<Boolean>()

    val error: LiveData<String> get() = mutableError
    val loading: LiveData<Boolean> get() = mutableLoading

    protected fun <T> onNext(mutableLiveData: MutableLiveData<T>): (T) -> Unit =
        { mutableLiveData.value = it }

    protected val onError: (Throwable) -> Unit =
        { e ->
            mutableError.value = e.customMessage()
            mutableLoading.value = false
        }

    protected val onComplete: () -> Unit = { mutableLoading.value = false }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}