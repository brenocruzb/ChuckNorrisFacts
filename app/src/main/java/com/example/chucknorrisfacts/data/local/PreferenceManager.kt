package com.example.chucknorrisfacts.data.local

import android.content.Context
import java.lang.NullPointerException

class PreferenceManager(context: Context) {
    private val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val categories = "categories"
    private val pastSearches = "past_searches"

    fun setCategories(categories: List<String>){
        setHashSet(this.categories, categories)
    }

    fun setPastSearches(pastSearches: List<String>){
        setHashSet(this.pastSearches, pastSearches)
    }

    fun delete(){
        val editor = preferences.edit()
        editor.remove(pastSearches)
        editor.apply()
    }

    private fun setHashSet(key: String, value: List<String>){
        val hashSet = HashSet<String>(value)
        val editor = preferences.edit()

        editor.putStringSet(key, hashSet)
        editor.apply()
    }

    fun getCategories(): List<String>{
        return preferences.getStringSet(categories, null)?.toList() ?:
        throw NullPointerException("Categories cannot be null.")
    }

    fun getPastSearches(): List<String>{
        return preferences.getStringSet(pastSearches, null)?.toList() ?: listOf()
    }

    fun hasCategories(): Boolean = preferences.contains(categories)
}