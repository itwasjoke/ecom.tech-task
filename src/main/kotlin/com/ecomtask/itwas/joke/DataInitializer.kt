package com.ecomtask.itwas.joke

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.service.CourseService
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
open class DataInitializer(
    private val userService: UserService,
    private val courseService: CourseService
) {
    @Bean
    open fun initializeData(): ApplicationRunner {
        return ApplicationRunner {
            val names = listOf("Иван Самоваров", "Андрей Васильев", "Маргарита Ненарокова", "Юрий Иванов")
            val ages = listOf(20, 21, 22, 33)
            val desc = listOf("Создатель школы", "Зам директора", "Учитель вселенной", null)
            val logins = listOf("log1", "log2", "log3", "log4")
            val passwords = listOf("pp1", "pp2", "pp3", "pp4")
            val userTypes = listOf("ADMIN", "TEACHER", "TEACHER", "NONE")
            for (i in names.indices){
                userService.createUser(
                    UserRequestDTO(
                    names[i],
                    ages[i],
                    logins[i],
                    passwords[i],
                    desc[i],
                    userTypes[i]
                )
                )
            }
            val calendar = Calendar.getInstance()
            val courseNames = listOf("Курс разрабокти", "Математика МК-14")
            val courseDesc = listOf("Изучаем Spring и Kotlin вместе!", "Для 5 классов")
            calendar.set(2024, Calendar.OCTOBER, 11)
            val dateStart = calendar.time
            calendar.set(2024, Calendar.DECEMBER, 11)
            val dateEnd = calendar.time
            for (i in courseNames.indices){
                courseService.createCourse(CourseRequestDTO(
                    courseNames[i],
                    courseDesc[i],
                    dateStart,
                    dateEnd
                ), "log1")
            }
            courseService.addStudentForCourse(2, 1)
            courseService.addStudentForCourse(3, 1)
            courseService.addStudentForCourse(4, 1)
        }
    }
}