package com.ecomtask.itwas.joke.repository

import com.ecomtask.itwas.joke.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>{
    fun findUserByLogin(login: String): User?

    fun existsUserByLogin(login: String): Boolean
    fun deleteUserByLogin(login: String)
}