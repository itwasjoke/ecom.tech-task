package com.ecomtask.itwas.joke.dto.mapping

import com.ecomtask.itwas.joke.dto.UserDTO
import com.ecomtask.itwas.joke.entity.User
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun userDTOtoUser(userDTO: UserDTO): User {
        return User(
            username = userDTO.name,
            password = userDTO.password,
            description = userDTO.description,
            userType = userDTO.userType,
            age = userDTO.age
        )
    }
}