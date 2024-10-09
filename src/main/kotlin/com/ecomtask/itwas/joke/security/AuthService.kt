package com.ecomtask.itwas.joke.security

import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.context.annotation.Lazy

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    @Lazy private val userService: UserService,
    private val jwtService: JwtService
    ) {
    fun auth(login: String, password: String): JwtResponse{
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(login, password))
        val user = userService.findUserByLogin(login);
        val jwt = "Bearer "+jwtService.generateToken(user);
        return JwtResponse(jwt)
    }
}