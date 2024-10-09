package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.security.AuthService
import com.ecomtask.itwas.joke.security.JwtResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    value = ["/auth"],
    produces = [MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"]
)
class AuthController(private val authService: AuthService) {
    @GetMapping
    fun signIn(@RequestParam login: String, @RequestParam password: String): JwtResponse{
        return authService.auth(login, password)
    }
}