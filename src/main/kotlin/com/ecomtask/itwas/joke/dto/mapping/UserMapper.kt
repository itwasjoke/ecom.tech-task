package com.ecomtask.itwas.joke.dto.mapping

import com.ecomtask.itwas.joke.dto.UserDTO
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.enums.UserType
import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun userDTOtoUser(userDTO: UserDTO): User {
        val errorMessage = "Unable to perform mapping for UserDTO to User due to incorrect format of received fields"
        try {
            val userType = UserType.valueOf(userDTO.userType)
            if (userDTO.name.isEmpty() || userDTO.password.isEmpty() || userDTO.age <= 0) {
                throw IncorrectUserFieldException(errorMessage)
            }
            return User(
                username = userDTO.name,
                password = userDTO.password,
                description = userDTO.description,
                userType = userType,
                age = userDTO.age
            )
        } catch (e: Exception){
            throw IncorrectUserFieldException(errorMessage)
        }
    }
}