package com.example.chucknorrisfacts.data.dao

import com.example.chucknorrisfacts.base.BaseRepository
import com.example.chucknorrisfacts.data.model.Category
import io.reactivex.Observable

class CategoriesRepository(private val categoriesDao: CategoriesDao): BaseRepository() {
    fun getAllSuggestions(): Observable<List<Category>>{
        return categoriesDao.getAllSuggestions().compose(applyObservableAsync()).take(1)
    }

    fun getAllPastSearchesByDate(): Observable<List<Category>>{
        return categoriesDao.getAllPastSearchesByDate().compose(applyObservableAsync()).take(1)
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

    fun update(category: Category){
        categoriesDao.update(category)
            .compose(applyCompletableAsync())
            .subscribe()
    }
}