package com.ecomtask.itwas.joke.controller

import com.ecomtask.itwas.joke.dto.UserDTO
import com.ecomtask.itwas.joke.dto.mapping.UserMapper
import com.ecomtask.itwas.joke.entity.User
import com.ecomtask.itwas.joke.enums.UserType
import com.ecomtask.itwas.joke.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
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


    @Test
    fun `createUser return created user ID`() {
        val userDTO = UserDTO(
            "testUser",
            1,
            "test",
            null,
            UserType.NONE
        );
        val createdUser = User(
            0,
            "testUser",
            1,
            "test",
            null,
            UserType.NONE,
            mutableSetOf()
        )
        val jsonRequest = objectMapper.writeValueAsString(userDTO)
        whenever(userMapper.userDTOtoUser(any())).thenReturn(createdUser)
        whenever(userService.createUser(any())).thenReturn(1L)

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("1"))
    }

}