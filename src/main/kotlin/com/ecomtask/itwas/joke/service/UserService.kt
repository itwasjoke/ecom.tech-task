package com.ecomtask.itwas.joke.service

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.entity.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService {
    fun createUser(userRequestDTO: UserRequestDTO): Long
    fun getUserProfile(login: String): UserResponseDTO
    fun editProfile(userRequestDTO: UserRequestDTO)
    fun findUserByLogin(login: String): User
    fun deleteUser(login: String)
    fun findUserById(id: Long): User
    fun getUserListOfCourse(courseId: Long): List<UserResponseDTO>
    fun getUserDetailsService(): UserDetailsService
}