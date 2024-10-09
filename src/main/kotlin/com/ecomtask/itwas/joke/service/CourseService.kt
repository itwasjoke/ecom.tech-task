package com.ecomtask.itwas.joke.service

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.entity.Course
import java.util.*

interface CourseService {
    fun createCourse(courseRequestDTO: CourseRequestDTO, login: String): Long
    fun getCourse(id: Long): CourseResponseDTO
    fun addStudentForCourse(studentId: Long, courseId: Long)
    fun deleteCourse(id: Long)
    fun findCourseById(id: Long): Course
    fun getStudentListOfCourse(id: Long): List<UserResponseDTO>
    fun deleteOldCourses(lateDate: Date)
}