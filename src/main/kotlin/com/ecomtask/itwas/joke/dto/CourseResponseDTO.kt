package com.ecomtask.itwas.joke.dto

import java.util.Date

data class CourseResponseDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val dateStart: Date,
    val dateEnd: Date,
    val creator: UserResponseDTO
)
