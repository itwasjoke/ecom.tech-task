package com.ecomtask.itwas.joke.service.impl

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.dto.mapping.UserMapper
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.exception.user.NoUserFoundException
import com.ecomtask.itwas.joke.repository.UserRepository
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
    ) : UserService {
    override fun createUser(userRequestDTO: UserRequestDTO): Long {
        val userForCreate = userMapper.userDTOtoUser(userRequestDTO);
        val createdUser = userRepository.save(userForCreate)
        return createdUser.id
    }

    override fun getUserProfile(login: String): UserResponseDTO {
        return userMapper.userToUserDTO(findUserByLogin(login))
    }

    override fun editProfile(userRequestDTO: UserRequestDTO) {
        val userRequest = userMapper.userDTOtoUser(userRequestDTO)
        val user = findUserByLogin(userRequest.login)
        user.userType = userRequest.userType
        user.age = userRequest.age
        user.description = userRequest.description
        user.username = userRequest.username
        userRepository.save(user)
    }

    override fun findUserByLogin(login: String): User {
        return userRepository.findUserByLogin(login) ?: throw NoUserFoundException("No user found in findUserByLogin fun.")
    }

    override fun deleteUser(login: String) {
        if (userRepository.existsUserByLogin(login)){
            userRepository.deleteUserByLogin(login)
        } else {
            throw NoUserFoundException("No user found in deleteUser fun.")
        }
    }
}