package com.gn4k.mvvm

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student")
data class Student(

    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name: String,
    val age: Int
)
