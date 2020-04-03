package com.example.chucknorrisfacts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chucknorrisfacts.base.BaseViewModel
import com.example.chucknorrisfacts.data.model.Fact

class MainViewModel: BaseViewModel(){

    private val listFacts = arrayListOf<Fact>()
    private val mutableFacts = MutableLiveData<List<Fact>>()
    val facts: LiveData<List<Fact>> get() = mutableFacts

    fun addFact(fact: Fact) {
        listFacts.add(fact)
        mutableFacts.value = listFacts
    }
}