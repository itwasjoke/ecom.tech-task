package com.ecomtask.itwas.joke.dto

data class UserRequestDTO(
    val name: String,
    val age: Int,
    val login: String,
    val password: String,
    val description: String?,
    val userType: String,
)
