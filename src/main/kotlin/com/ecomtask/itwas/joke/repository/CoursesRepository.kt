package com.ecomtask.itwas.joke.repository

import com.ecomtask.itwas.joke.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CoursesRepository: JpaRepository<Course, Long> {
    fun findAllById(id: Long): Course?
}