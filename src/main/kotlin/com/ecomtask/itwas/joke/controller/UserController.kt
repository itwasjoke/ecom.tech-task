package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.service.UserService
import jakarta.servlet.http.HttpServletRequest
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
    fun getUserProfile(request: HttpServletRequest): UserResponseDTO {
        val auth = request.userPrincipal
        val userDetails = auth.name
        return userService.getUserProfile(userDetails)
    }
    @PutMapping
    fun editUserProfile(@RequestBody userRequestDTO: UserRequestDTO, request: HttpServletRequest) {
        val auth = request.userPrincipal
        val userDetails = auth.name
        userService.editProfile(userRequestDTO, userDetails)
    }
    @DeleteMapping
    fun deleteUser(@RequestParam login: String) {
        userService.deleteUser(login)
    }
}