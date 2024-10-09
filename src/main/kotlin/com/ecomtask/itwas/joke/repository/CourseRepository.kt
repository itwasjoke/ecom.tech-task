package com.ecomtask.itwas.joke.repository

import com.ecomtask.itwas.joke.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository: JpaRepository<Course, Long> {
    fun findCourseById(id: Long): Course?
    fun existsByIdAndStudents_Id(courseId: Long, userId: Long): Boolean
}