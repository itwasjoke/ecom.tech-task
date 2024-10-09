package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserRequestDTO
import com.ecomtask.itwas.joke.dto.UserResponseDTO
import com.ecomtask.itwas.joke.dto.mapping.UserMapper
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.customenum.UserType
import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import com.ecomtask.itwas.joke.exception.user.NoUserFoundException
import com.ecomtask.itwas.joke.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

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

    private val validUserRequestDTO = UserRequestDTO(
        "testUser",
        1,
        "test",
        "qwerty",
        null,
        "NONE"
    )

    private val invalidUserRequestDTO = UserRequestDTO(
        "",
        -50,
        "test",
        "",
        null,
        "error"
    )

    private val createdUser = User(
        0,
        "testUser",
        "test",
        1,
        "qwerty",
        null,
        UserType.NONE,
        mutableSetOf()
    )

    private val updatedUser = UserRequestDTO(
        "Andrey Vasilev",
        20,
        "test",
        "dfcbkmtd",
        "I'm backend developer",
        "ADMIN"
    )

    private val userProfile = UserResponseDTO(
        "testUser",
        1,
        "Нет роли",
        null
    )

    @Test
    fun `createUser return created user ID`() {
        val jsonRequest = objectMapper.writeValueAsString(validUserRequestDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        performPostRequest(
            jsonRequest,
            HttpStatus.OK,
            "application/json;charset=UTF-8",
            "1"
        )
    }

    @Test
    fun `createUser return exception`() {
        val jsonRequest = objectMapper.writeValueAsString(invalidUserRequestDTO)
        val errorMessage = "Unable to perform mapping for UserDTO to User due to incorrect format of received fields"
        whenever(userService.createUser(any()))
            .thenThrow(IncorrectUserFieldException(errorMessage))

        performPostRequest(
            jsonRequest,
            HttpStatus.BAD_REQUEST,
            "text/plain;charset=UTF-8",
            "Неправильный формат данных о пользователе."
        )
    }

    @Test
    fun `getUserProfile return UserResponseDTO`() {
        val jsonRequest = objectMapper.writeValueAsString(validUserRequestDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        performPostRequest(
            jsonRequest,
            HttpStatus.OK,
            "application/json;charset=UTF-8",
            "1"
        )

        whenever(userService.getUserProfile(any())).thenReturn(userProfile)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("login", "test")
        val jsonResponse = objectMapper.writeValueAsString(userProfile)
        performGetRequest(
            params,
            HttpStatus.OK,
            "application/json;charset=UTF-8",
            jsonResponse
        )
    }

    @Test
    fun `getUserProfile return exception`() {
        val jsonRequest = objectMapper.writeValueAsString(validUserRequestDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        performPostRequest(
            jsonRequest,
            HttpStatus.OK,
            "application/json;charset=UTF-8",
            "1"
        )
        val errorMessage = "No user found in findUserByLogin fun."
        whenever(userService.getUserProfile(any()))
            .thenThrow(NoUserFoundException(errorMessage))

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("login", "nofound")
        performGetRequest(
            params,
            HttpStatus.NOT_FOUND,
            "text/plain;charset=UTF-8",
            "Пользователь не найден."
        )
    }

    @Test
    fun `editProfile changed User and return nothing`() {
        val jsonRequest = objectMapper.writeValueAsString(validUserRequestDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        performPostRequest(
            jsonRequest,
            HttpStatus.OK,
            "application/json;charset=UTF-8",
            "1"
        )
        doNothing().whenever(userService).editProfile(any())
        val jsonRequestUpdateUser = objectMapper.writeValueAsString(updatedUser)
        performPutRequest(
            jsonRequestUpdateUser,
            HttpStatus.OK
        )
    }

    @Test
    fun `deleteProfile deleting User and return nothing`() {
        val jsonRequest = objectMapper.writeValueAsString(validUserRequestDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        performPostRequest(
            jsonRequest,
            HttpStatus.OK,
            "application/json;charset=UTF-8",
            "1"
        )
        doNothing().whenever(userService).deleteUser(any())
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("login", "test")
        performDeleteRequest(
            params,
            HttpStatus.OK
        )
    }

    private fun performPostRequest(
        jsonRequest: String,
        expectedStatus: HttpStatus,
        expectedContentType: String,
        expectedBody: String
    ) {
        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest)
        )
            .andExpect(status().`is`(expectedStatus.value()))
            .andExpect(content().contentType(expectedContentType))
            .andExpect(content().string(expectedBody))
    }
    private fun performGetRequest(
        params: MultiValueMap<String, String>,
        expectedStatus: HttpStatus,
        expectedContentType: String, expectedBody: String
    ) {
        mockMvc.perform(
            get("/users")
                .queryParams(params)
        )
            .andExpect(status().`is`(expectedStatus.value()))
            .andExpect(content().contentType(expectedContentType))
            .andExpect(content().string(expectedBody))
    }
    private fun performPutRequest(jsonRequest: String, expectedStatus: HttpStatus) {
        mockMvc.perform(
            put("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest)
        )
            .andExpect(status().`is`(expectedStatus.value()))
    }
    private fun performDeleteRequest(params: MultiValueMap<String, String>, expectedStatus: HttpStatus) {
        mockMvc.perform(
            delete("/users")
                .queryParams(params)
        )
            .andExpect(status().`is`(expectedStatus.value()))
    }
}