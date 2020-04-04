package com.example.chucknorrisfacts.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chucknorrisfacts.data.model.Category
import com.example.chucknorrisfacts.data.model.Converters

@Database(entities = [Category::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun categoryDao(): CategoriesDao
}