package com.ecomtask.itwas.joke.service

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.entity.Course

interface CourseService {
    fun createCourse(courseRequestDTO: CourseRequestDTO): Long
    fun getCourse(id: Long): CourseResponseDTO
    fun addStudentForCourse(studentId: Long, courseId: Long)
    fun deleteCourse(id: Long)
    fun findCourseById(id: Long): Course
}