package com.ecomtask.itwas.joke.exception

import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import com.ecomtask.itwas.joke.exception.user.NoUserFoundException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Slf4j
@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleIncorrectUserFieldsException(e: IncorrectUserFieldException, request: WebRequest): ResponseEntity<Any>? {
        val body = "Неправильный формат данных о пользователе."
        return handleExceptionInternal(e, body, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }
    @ExceptionHandler
    fun handleNoUserFoundException(e: NoUserFoundException, request: WebRequest): ResponseEntity<Any>? {
        val body = "Пользователь не найден."
        return handleExceptionInternal(e, body, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

}