package com.example.assignment2.database.course

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Holds course data for both course list and course schedule
@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "cid") val courseId: String,
    @ColumnInfo(name = "name") val courseName: String,
    @ColumnInfo(name = "term") val courseTerm: Int,
    @ColumnInfo(name = "prereq") val coursePrereq: String
) {
    override fun toString(): String {
        return "$courseId $courseName"
    }
}