package com.ecomtask.itwas.joke.service.impl

import com.ecomtask.itwas.joke.dto.UserDTO
import com.ecomtask.itwas.joke.dto.mapping.UserMapper
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.enums.UserType
import com.ecomtask.itwas.joke.repository.UserRepository
import com.ecomtask.itwas.joke.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val userMapper: UserMapper) : UserService {
    override fun createUser(userDTO: UserDTO): Long {
        val userForCreate = userMapper.userDTOtoUser(userDTO);
        val createdUser = userRepository.save(userForCreate)
        return createdUser.id
    }
}