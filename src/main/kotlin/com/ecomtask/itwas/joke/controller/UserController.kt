package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserDTO
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/users"])
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO): Long {
        return userService.createUser(userDTO);
    }

}