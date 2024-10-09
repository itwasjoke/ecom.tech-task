package com.ecomtask.itwas.joke.service.impl

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.dto.mapping.CourseMapper
import com.ecomtask.itwas.joke.entity.Course
import com.ecomtask.itwas.joke.exception.course.NoCourseFoundException
import com.ecomtask.itwas.joke.exception.course.StudentAlreadyExistsException
import com.ecomtask.itwas.joke.repository.CourseRepository
import com.ecomtask.itwas.joke.service.CourseService
import com.ecomtask.itwas.joke.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
open class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseMapper: CourseMapper,
    private val userService: UserService
    ): CourseService {
    override fun createCourse(courseRequestDTO: CourseRequestDTO): Long {
        val creator = userService.findUserById(courseRequestDTO.creator)
        val course = courseRepository.save(courseMapper.courseDTOtoCourse(courseRequestDTO, creator))
        return course.id
    }
    override fun getCourse(id: Long): CourseResponseDTO {
        val course = courseRepository.findCourseById(id)
            ?: throw NoCourseFoundException("No course found with students")
        return courseMapper.courseToCourseDTO(course)
    }

    override fun addStudentForCourse(studentId: Long, courseId: Long) {
        if (courseRepository.existsByIdAndStudents_Id(courseId, studentId)) {
            throw StudentAlreadyExistsException("User already exists to adding to course in addStudentForCourse fun")
        }
        val user = userService.findUserById(studentId)
        val course = findCourseById(courseId)
        course.students.add(user)
        courseRepository.save(course)
    }

    override fun deleteCourse(id: Long) {
        val course = findCourseById(id)
        courseRepository.delete(course)
    }

    override fun findCourseById(id: Long): Course {
        return courseRepository.findCourseById(id) ?: throw NoCourseFoundException("No course found in findCourseById fun")
    }

    override fun getStudentListOfCourse(id: Long): List<UserResponseDTO> {
        return userService.getUserListOfCourse(id)
    }

    override fun deleteOldCourses(lateDate: Date) {
        courseRepository.deleteByDateEndBefore(lateDate)
    }
}