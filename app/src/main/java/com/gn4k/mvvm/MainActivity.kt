package com.gn4k.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val usernameInput = findViewById<EditText>(R.id.username)
        val passInput = findViewById<EditText>(R.id.pass)
        val signin: Button = findViewById(R.id.signin)
        val signup: Button = findViewById(R.id.signup)


        database = StudentDatabase.getDatabase(applicationContext)
        val studentDao = database.studentDao()


        signin.setOnClickListener {

            val username = usernameInput.text.toString()
            val pass = passInput.text.toString().toIntOrNull()

            if (username.isNotEmpty() && pass != null) {
                val studentLiveData = studentDao.getStudentByNameAndAge(username, pass)
                studentLiveData.observe(this) { students ->
                    if (students.isNotEmpty()) {

                        Toast.makeText(
                            this,
                            "Sign-in successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        usernameInput.setText("")
                        passInput.setText("")
                    } else {

                        Toast.makeText(
                            this,
                            "Sign-in failed: Invalid credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter valid username and pass", Toast.LENGTH_SHORT).show()
            }

        }

        signup.setOnClickListener {

            val username = usernameInput.text.toString()
            val pass = passInput.text.toString().toInt()
            database = Room.databaseBuilder(
                applicationContext,
                StudentDatabase::class.java,
                "studentDB"
            ).build()

            GlobalScope.launch {
                database.studentDao().insertStudent(Student(0, username, pass))
            }
            Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show()
            usernameInput.setText("")
            passInput.setText("")
        }

        database = Room.databaseBuilder(
            applicationContext,
            StudentDatabase::class.java,
            "studentDB"
        ).build()
        GlobalScope.launch {
            database.studentDao().insertStudent(Student(0, "username", 0))
        }

    }
}