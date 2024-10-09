package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    value = ["/users"],
    produces = [MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"]
)
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody userRequestDTO: UserRequestDTO): Long {
        return userService.createUser(userRequestDTO);
    }
    @GetMapping
    fun getUserProfile(@RequestParam login: String): UserResponseDTO {
        return userService.getUserProfile(login)
    }
    @PutMapping
    fun editUserProfile(@RequestBody userRequestDTO: UserRequestDTO) {
        userService.editProfile(userRequestDTO)
    }
    @DeleteMapping
    fun deleteUser(@RequestParam login: String) {
        userService.deleteUser(login)
    }
}