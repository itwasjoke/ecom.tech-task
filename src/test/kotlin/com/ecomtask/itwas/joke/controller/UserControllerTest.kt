package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserDTO
import com.ecomtask.itwas.joke.dto.mapping.UserMapper
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.enums.UserType
import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import com.ecomtask.itwas.joke.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserController::class)
@ExtendWith(MockitoExtension::class)
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val validUserDTO = UserDTO(
        "testUser",
        1,
        "test",
        null,
        "NONE"
    )

    private val invalidUserDTO = UserDTO(
        "",
        -50,
        "test",
        null,
        "Nfaafsdf"
    )

    private val createdUser = User(
        0,
        "testUser",
        1,
        "test",
        null,
        UserType.NONE,
        mutableSetOf()
    )

    @Test
    fun `createUser return created user ID`() {
        val jsonRequest = objectMapper.writeValueAsString(validUserDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        performPostRequest(
            jsonRequest,
            HttpStatus.OK,
            MediaType.APPLICATION_JSON_VALUE,
            "1"
        )
    }

    @Test
    fun `createUser return exception`() {
        val jsonRequest = objectMapper.writeValueAsString(invalidUserDTO)
        val errorMessage = "Unable to perform mapping for UserDTO to User due to incorrect format of received fields"
        whenever(userService.createUser(any())).thenThrow(IncorrectUserFieldException(errorMessage))

        performPostRequest(
            jsonRequest,
            HttpStatus.BAD_REQUEST,
            "text/plain;charset=UTF-8",
            "Неправильный формат данных о пользователе."
        )
    }

    private fun performPostRequest(jsonRequest: String, expectedStatus: HttpStatus, expectedContentType: String, expectedBody: String) {
        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().`is`(expectedStatus.value()))
            .andExpect(content().contentType(expectedContentType))
            .andExpect(content().string(expectedBody))
    }
}