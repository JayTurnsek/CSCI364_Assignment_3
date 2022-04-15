package com.example.assignment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment2.database.course.Course
import com.example.assignment2.database.course.CourseDAO

@Database(entities = [Course::class], version = 2, exportSchema = false)
abstract class CourseListDatabase : RoomDatabase() {

    // Get DAO object
    abstract fun courseListDao(): CourseDAO

    companion object {
        @Volatile
        private var INSTANCE: CourseListDatabase? = null

        // Gets database from previously created .db file
        fun getDatabase(context: Context): CourseListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CourseListDatabase::class.java,
                    "course_list_database"
                )
                    .createFromAsset("database/course_data.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}