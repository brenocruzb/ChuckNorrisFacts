package com.example.chucknorrisfacts.data.dao

import com.example.chucknorrisfacts.base.BaseRepository
import com.example.chucknorrisfacts.data.model.Category
import io.reactivex.Observable

class CategoriesRepository(private val categoriesDao: CategoriesDao): BaseRepository() {
    fun getAllSuggestions(): Observable<List<Category>>{
        return categoriesDao.getAllSuggestions().compose(applyObservableAsync())
    }

    fun getAllPastSearches(): Observable<List<Category>>{
        return categoriesDao.getAllPastSearches().compose(applyObservableAsync())
    }

    fun insertAll(categories: List<Category>) {
        categoriesDao.insertAll(*categories.toTypedArray())
            .compose(applyCompletableAsync())
            .subscribe()
    }

    fun insert(category: Category){
        categoriesDao.insertAll(category)
            .compose(applyCompletableAsync())
            .subscribe()
    }

    fun delete(category: Category){
        categoriesDao.delete(category)
            .compose(applyCompletableAsync())
            .subscribe()
    }
}