package com.ecomtask.itwas.joke.service.impl

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.dto.mapping.UserMapper
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.exception.user.LoginAlreadyExistsException
import com.ecomtask.itwas.joke.exception.user.NoUserFoundException
import com.ecomtask.itwas.joke.repository.UserRepository
import com.ecomtask.itwas.joke.service.UserService
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.context.annotation.Lazy

@Service
@Transactional
open class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    @Lazy private val passwordEncoder: PasswordEncoder
    ) : UserService {
    override fun createUser(userRequestDTO: UserRequestDTO): Long {
        if (userRepository.existsUserByLogin(userRequestDTO.login)){
            throw LoginAlreadyExistsException("User with login already exist at createUser fun")
        }
        val userForCreate = userMapper.userDTOtoUser(userRequestDTO);
        val password: String = passwordEncoder.encode(userForCreate.rawPassword)
        userForCreate.rawPassword = password
        val createdUser = userRepository.save(userForCreate)
        return createdUser.id
    }

    override fun getUserProfile(login: String): UserResponseDTO {
        return userMapper.userToUserDTO(findUserByLogin(login))
    }

    override fun editProfile(userRequestDTO: UserRequestDTO, currentLogin: String) {
        if (userRequestDTO.login != currentLogin) {
            if (userRepository.existsUserByLogin(userRequestDTO.login)) {
                throw LoginAlreadyExistsException("Login exists on editProfile fun")
            }
        }
        val userRequest = userMapper.userDTOtoUser(userRequestDTO)
        val user = findUserByLogin(userRequest.login)
        user.userType = userRequest.userType
        user.age = userRequest.age
        user.login = userRequest.login
        user.description = userRequest.description
        user.fullname = userRequest.fullname
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

    override fun findUserById(id: Long): User {
        return userRepository.findUserById(id) ?: throw NoUserFoundException("No user found in findUserById fun.")
    }

    override fun getUserListOfCourse(courseId: Long): List<UserResponseDTO> {
        return userMapper.userListToUserDTOList(userRepository.findAllByCourses_Id(courseId))
    }

    override fun getUserDetailsService(): UserDetailsService {
        return UserDetailsService { login -> findUserByLogin(login) }
    }
}