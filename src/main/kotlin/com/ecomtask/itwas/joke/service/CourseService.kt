package com.ecomtask.itwas.joke.service

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO

interface CourseService {
    fun createCourse(courseRequestDTO: CourseRequestDTO): Long
    fun getCourse(id: Long): CourseResponseDTO
    fun changeCourse(courseRequestDTO: CourseRequestDTO)
    fun deleteCourse(id: Long)
}