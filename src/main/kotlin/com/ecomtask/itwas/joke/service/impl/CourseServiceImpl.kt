package com.ecomtask.itwas.joke.service.impl

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.dto.mapping.CourseMapper
import com.ecomtask.itwas.joke.entity.Course
import com.ecomtask.itwas.joke.exception.course.NoCourseFoundException
import com.ecomtask.itwas.joke.repository.CoursesRepository
import com.ecomtask.itwas.joke.service.CourseService
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    private val coursesRepository: CoursesRepository,
    private val courseMapper: CourseMapper,
    private val userService: UserService
    ): CourseService {
    override fun createCourse(courseRequestDTO: CourseRequestDTO): Long {
        val creator = userService.findUserByLogin(courseRequestDTO.creator)
        val course = coursesRepository.save(courseMapper.courseDTOtoCourse(courseRequestDTO, creator))
        return course.id
    }
    override fun getCourse(id: Long): CourseResponseDTO {
        return courseMapper.courseToCourseDTO(findCourseById(id))
    }

    override fun addStudentForCourse(studentId: Long, courseId: Long) {
        val user = userService.findUserById(studentId)
        val course = findCourseById(courseId)
        course.students.add(user)
        coursesRepository.save(course)
    }

    override fun deleteCourse(id: Long) {
        val course = findCourseById(id)
        coursesRepository.delete(course)
    }

    override fun findCourseById(id: Long): Course {
        return coursesRepository.findCourseById(id) ?: throw NoCourseFoundException("No course found in findCourseById fun")
    }
}