package com.ecomtask.itwas.joke.dto

import com.ecomtask.itwas.joke.enums.UserType

data class UserDTO(
    val name: String,
    val age: Int,
    val password: String,
    val description: String?,
    val userType: UserType,
)
