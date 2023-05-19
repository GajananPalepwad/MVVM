package com.gn4k.mvvm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDAO {

    @Insert
    fun insertStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Query("SELECT * FROM student WHERE name = :name")
    fun getStudentByName(name: String): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE name = :name AND age = :age")
    fun getStudentByNameAndAge(name: String, age: Int): LiveData<List<Student>>
}
