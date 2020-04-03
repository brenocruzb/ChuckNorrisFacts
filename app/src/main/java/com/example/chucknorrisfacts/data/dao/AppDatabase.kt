package com.example.chucknorrisfacts.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chucknorrisfacts.data.model.Category

@Database(entities = [Category::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun categoryDao(): CategoriesDao
}