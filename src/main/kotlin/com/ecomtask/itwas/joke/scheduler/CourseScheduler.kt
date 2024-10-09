package com.ecomtask.itwas.joke.scheduler

import com.ecomtask.itwas.joke.service.CourseService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class CourseScheduler(private val courseService: CourseService) {
    @Scheduled(cron = "0 0 0 1 * ?")
    fun clearOldCourses() {
        val oneYearAgo = Calendar.getInstance().apply {
            add(Calendar.YEAR, -1)
        }.time
        courseService.deleteOldCourses(oneYearAgo)
    }
}