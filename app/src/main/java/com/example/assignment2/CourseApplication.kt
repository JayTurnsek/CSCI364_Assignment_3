package com.example.assignment2

import android.app.Application
import com.example.assignment2.database.CourseScheduleDatabase
import com.example.assignment2.database.CourseScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CourseApplication : Application() {

    // check this import? supervisorJob
    private val applicationScope = CoroutineScope(SupervisorJob())

    // load database and repository only when needed
    val database by lazy { CourseScheduleDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CourseScheduleRepository(database.courseScheduleDao()) }

}