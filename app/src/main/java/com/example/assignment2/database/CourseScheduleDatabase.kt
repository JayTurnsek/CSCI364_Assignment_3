package com.example.assignment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.assignment2.database.course.Course
import com.example.assignment2.database.course.CourseDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Course::class], version = 2, exportSchema = false)
abstract class CourseScheduleDatabase : RoomDatabase() {

    abstract fun courseScheduleDao(): CourseDAO


    private class CourseScheduleDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                {
                    scope.launch {
                        var courseDataDao = database.courseScheduleDao()

                        // delete all data
                        database.clearAllTables()

                    }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CourseScheduleDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CourseScheduleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CourseScheduleDatabase::class.java,
                    "selected_course_database"
                )
                    .allowMainThreadQueries()
                    .addCallback(CourseScheduleDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }

}