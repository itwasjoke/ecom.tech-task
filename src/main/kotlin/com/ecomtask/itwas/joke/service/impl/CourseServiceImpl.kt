package com.ecomtask.itwas.joke.service.impl

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.service.CourseService
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl: CourseService {
    override fun createCourse(courseRequestDTO: CourseRequestDTO): Long {
        TODO("Not yet implemented")
    }

    override fun getCourse(id: Long): CourseResponseDTO {
        TODO("Not yet implemented")
    }

    override fun changeCourse(courseRequestDTO: CourseRequestDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteCourse(id: Long) {
        TODO("Not yet implemented")
    }
}