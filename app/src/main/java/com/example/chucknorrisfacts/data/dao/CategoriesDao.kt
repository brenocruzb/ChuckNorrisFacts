package com.example.chucknorrisfacts.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.chucknorrisfacts.data.model.Category
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category where is_suggestion = 1")
    fun getAllSuggestions(): Observable<List<Category>>

    @Query("SELECT * FROM category where is_suggestion = 0")
    fun getAllPastSearches(): Observable<List<Category>>

    @Insert
    fun insertAll(vararg categories: Category): Completable

    @Delete
    fun delete(category: Category): Completable
}