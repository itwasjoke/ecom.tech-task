package com.ecomtask.itwas.joke.dto

import java.util.Date

data class CourseRequestDTO(
    var name: String,
    var description: String,
    var dateStart: Date,
    var dateEnd: Date,
    var creator: Long
)
