package com.example.assignment2.database.course

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Interface for both course list and course schedule queries
@Dao
interface CourseDAO {

    // QUERIES BELOW FOR THE SELECTED COURSES DATABASE
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM courses WHERE term = 1")
    fun getFirstTerm(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE term = 2")
    fun getSecondTerm(): Flow<List<Course>>

    @Query("DELETE FROM courses")
    suspend fun deleteAll()

    //
    //
    // QUERY BELOW USED FOR COURSE LIST DATABASE
    @Query("SELECT * FROM courses")
    fun getAll(): List<Course>
}