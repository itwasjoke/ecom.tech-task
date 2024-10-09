package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/users"])
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody userRequestDTO: UserRequestDTO): Long {
        return userService.createUser(userRequestDTO);
    }
    @GetMapping
    fun getUserProfile(@RequestParam login: String) {
        userService.getUserProfile(login)
    }
    @PutMapping
    fun editUserProfile(@RequestBody userRequestDTO: UserRequestDTO) {
        userService.editProfile(userRequestDTO)
    }
    @DeleteMapping
    fun deleteUser(@RequestParam login: String) {

    }

}