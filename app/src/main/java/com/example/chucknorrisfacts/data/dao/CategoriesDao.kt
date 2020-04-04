package com.example.chucknorrisfacts.data.dao

import androidx.room.*
import com.example.chucknorrisfacts.data.model.Category
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category where is_suggestion = 1")
    fun getAllSuggestions(): Observable<List<Category>>

    @Query("SELECT * FROM category where is_suggestion = 0 order by date desc")
    fun getAllPastSearchesByDate(): Observable<List<Category>>

    @Insert
    fun insertAll(vararg categories: Category): Completable

    @Delete
    fun delete(category: Category): Completable

    @Update
    fun update(category: Category): Completable
}