package com.example.assignment2.database

import androidx.annotation.WorkerThread
import com.example.assignment2.database.course.Course
import com.example.assignment2.database.course.CourseDAO
import kotlinx.coroutines.flow.Flow

class CourseScheduleRepository(private val courseScheduleDao: CourseDAO) {

    val firstTermCourses: Flow<List<Course>> = courseScheduleDao.getFirstTerm()
    val secondTermCourses: Flow<List<Course>> = courseScheduleDao.getSecondTerm()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(course: Course) {
        courseScheduleDao.insertCourse(course)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() {
        courseScheduleDao.deleteAll()
    }
}