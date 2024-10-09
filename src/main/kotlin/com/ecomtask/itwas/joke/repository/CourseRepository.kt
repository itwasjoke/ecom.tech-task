package com.ecomtask.itwas.joke.repository

import com.ecomtask.itwas.joke.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CourseRepository: JpaRepository<Course, Long> {
    fun findCourseById(id: Long): Course?
    fun existsByIdAndStudents_Id(courseId: Long, userId: Long): Boolean
    @Modifying
    @Query("DELETE FROM Course c WHERE c.dateEnd < :date")
    fun deleteByDateEndBefore(@Param("date") date: Date)
}