package com.ecomtask.itwas.joke.service

import com.ecomtask.itwas.joke.dto.UserDTO

interface UserService {
    fun createUser(userDTO: UserDTO): Long
}