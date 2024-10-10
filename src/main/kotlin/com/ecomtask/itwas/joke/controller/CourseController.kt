package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.service.CourseService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    value = ["/courses"],
    produces = [MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"]
)
class CourseController(private val courseService: CourseService) {
    @PostMapping
    fun createCourse(@RequestBody courseRequestDTO: CourseRequestDTO, request: HttpServletRequest): Long {
        val auth = request.userPrincipal
        val userDetails = auth.name
        return courseService.createCourse(courseRequestDTO, userDetails)
    }
    @GetMapping("{id}")
    fun getCourse(@PathVariable id: Long): CourseResponseDTO {
        return courseService.getCourse(id)
    }
    @GetMapping("/list/{id}")
    fun getStudentsList(@PathVariable id: Long): List<UserResponseDTO> {
        return courseService.getStudentListOfCourse(id)
    }
    @PutMapping
    fun editCourseMainInfo(@RequestParam studentId: Long, @RequestParam courseId: Long) {
        courseService.addStudentForCourse(studentId, courseId)
    }
    @DeleteMapping("{id}")
    fun deleteCourse(@PathVariable id: Long) {
        courseService.deleteCourse(id)
    }
}