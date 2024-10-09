package com.ecomtask.itwas.joke.repository

import com.ecomtask.itwas.joke.entity.Course
import com.ecomtask.itwas.joke.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CoursesRepository: JpaRepository<Course, Long> {
    fun findCourseById(id: Long): Course?
    fun existsByIdAndStudents_Id(courseId: Long, userId: Long): Boolean

    @Query("select c from Course c left join fetch c.students where c.id = :courseId")
    fun findCourseAndStudentsById(@Param("courseId") courseId: Long): Course?

}