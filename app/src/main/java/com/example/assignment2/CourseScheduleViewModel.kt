package com.example.assignment2

import androidx.lifecycle.*
import com.example.assignment2.database.CourseScheduleRepository
import com.example.assignment2.database.course.Course
import kotlinx.coroutines.launch

class CourseScheduleViewModel(private val repository: CourseScheduleRepository) : ViewModel() {

    val firstTermCourses: LiveData<List<Course>> = repository.firstTermCourses.asLiveData()
    val secondTermCourses: LiveData<List<Course>> = repository.secondTermCourses.asLiveData()


    fun insert(course: Course) = viewModelScope.launch {
        repository.insert(course)
    }

    fun clear() = viewModelScope.launch {
        repository.clear()
    }
}

class CourseScheduleViewModelFactory(private val repository: CourseScheduleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}