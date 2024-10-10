package com.ecomtask.itwas.joke.dto.mapping

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.enumc.UserType
import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun userDTOtoUser(userRequestDTO: UserRequestDTO): User {
        val errorMessage = "Unable to perform mapping for UserDTO to User due to incorrect format of received fields"
        try {
            val userType = UserType.valueOf(userRequestDTO.userType)
            if (userRequestDTO.name.isEmpty()
                || userRequestDTO.login.isEmpty()
                || userRequestDTO.password.isEmpty()
                || userRequestDTO.age <= 0
            ) {
                throw IncorrectUserFieldException(errorMessage)
            }
            val user = User()
            user.fullname = userRequestDTO.name
            user.login = userRequestDTO.login
            user.rawPassword = userRequestDTO.password
            user.description = userRequestDTO.description
            user.userType = userType
            user.age = userRequestDTO.age
            return user
        } catch (e: Exception){
            throw IncorrectUserFieldException(errorMessage)
        }
    }
    fun userToUserDTO(user: User): UserResponseDTO {
        var userRole = when(user.userType){
            UserType.NONE -> "Нет роли"
            UserType.ADMIN -> "Администратор"
            UserType.STUDENT -> "Cтудент"
            UserType.TEACHER -> "Преподаватель"
        }
        return UserResponseDTO(
            id = user.id,
            name = user.fullname,
            login = user.login,
            age = user.age,
            type = userRole,
            description = user.description
        )
    }
    fun userListToUserDTOList(userList: MutableList<User>): List<UserResponseDTO>{
        var userResponseList: MutableList<UserResponseDTO> = mutableListOf()
        for (user in userList) {
            userResponseList.add(userToUserDTO(user))
        }
        return userResponseList
    }
}