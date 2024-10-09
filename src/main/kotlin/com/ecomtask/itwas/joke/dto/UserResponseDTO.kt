package com.ecomtask.itwas.joke.dto

data class UserResponseDTO(
    val id: Long,
    val name: String,
    val age: Int,
    val type: String,
    val description: String?
)
