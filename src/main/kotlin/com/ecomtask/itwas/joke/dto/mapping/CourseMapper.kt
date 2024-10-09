package com.ecomtask.itwas.joke.dto.mapping

import com.ecomtask.itwas.joke.dto.CourseRequestDTO
import com.ecomtask.itwas.joke.dto.CourseResponseDTO
import com.ecomtask.itwas.joke.entity.Course
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.exception.course.EmptyFieldsCourseException
import com.ecomtask.itwas.joke.exception.course.InvalidDatesForCourseException
import com.ecomtask.itwas.joke.exception.course.UnknownCourseDataException
import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import org.springframework.stereotype.Component
import java.util.Date

@Component
class CourseMapper(private val userMapper: UserMapper) {
    fun courseDTOtoCourse(courseRequestDTO: CourseRequestDTO, creator: User): Course{
        if (courseRequestDTO.name.isEmpty()
            || courseRequestDTO.description.isEmpty()
        ) {
            throw EmptyFieldsCourseException("Empty fields in courseDTOtoCourse fun")
        } else if (
            courseRequestDTO.dateStart < Date()
            || courseRequestDTO.dateStart > courseRequestDTO.dateEnd
        ) {
            throw InvalidDatesForCourseException("Incorrect dates in courseDTOtoCourse fun")
        } else {
            return Course(
                id = 0,
                name = courseRequestDTO.name,
                description = courseRequestDTO.description,
                dateStart = courseRequestDTO.dateStart,
                dateEnd = courseRequestDTO.dateEnd,
                students = mutableSetOf(),
                creator = creator
            )
        }
    }
    fun courseToCourseDTO(course: Course): CourseResponseDTO {
        val listOfStudents = userMapper.userListToUserDTOList(course.students)
        val creator = course.creator?.let { userMapper.userToUserDTO(it) }
        if (creator != null) {
            return CourseResponseDTO(
                id = course.id,
                name = course.name,
                description = course.description,
                dateStart = course.dateStart,
                dateEnd = course.dateEnd,
                creator = creator,
                students = listOfStudents
            )
        } else {
            throw UnknownCourseDataException("No User for Course creator.")
        }
    }
}