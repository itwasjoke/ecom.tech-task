package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.service.CourseService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    value = ["/courses"],
    produces = [MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"]
)
class CourseController(private val courseService: CourseService) {
    @PostMapping
    fun createCourse(@RequestBody courseRequestDTO: CourseRequestDTO): Long {
        return courseService.createCourse(courseRequestDTO);
    }
    @GetMapping
    fun getCourse(@RequestParam id: Long): CourseResponseDTO {
        return courseService.getCourse(id)
    }
    @PutMapping
    fun editCourseMainInfo(@RequestParam studentId: Long, @RequestParam courseId: Long) {
        courseService.addStudentForCourse(studentId, courseId)
    }
    @DeleteMapping
    fun deleteCourse(@RequestParam id: Long) {
        courseService.deleteCourse(id)
    }
}