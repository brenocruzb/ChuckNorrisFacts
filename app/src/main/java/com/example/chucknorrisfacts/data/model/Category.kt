package com.example.chucknorrisfacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Category(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_suggestion") val isSuggestion: Boolean,
    @ColumnInfo(name = "date") var date: Date,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)