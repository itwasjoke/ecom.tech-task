package com.ecomtask.itwas.joke.exception

import com.ecomtask.itwas.joke.exception.course.*
import com.ecomtask.itwas.joke.exception.user.IncorrectUserFieldException
import com.ecomtask.itwas.joke.exception.user.LoginAlreadyExistsException
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

    @ExceptionHandler(
        IncorrectUserFieldException::class,
        NoUserFoundException::class,
        LoginAlreadyExistsException::class,
        EmptyFieldsCourseException::class,
        InvalidDatesForCourseException::class,
        NoCourseFoundException::class,
        StudentAlreadyExistsException::class,
        UnknownCourseDataException::class
    )
    fun handleAllException(e: Exception, request: WebRequest): ResponseEntity<Any>? {
        val responseBody = when (e) {
            is IncorrectUserFieldException -> "Неправильный формат данных о пользователе."
            is NoUserFoundException -> "Пользователь не найден."
            is LoginAlreadyExistsException -> "Пользователь с таким логином уже существует."
            is EmptyFieldsCourseException -> "Пустые поля в курсе."
            is InvalidDatesForCourseException -> "Даты некорректны. Дата начала должна быть больше текущей. Дата конца должна быть дальше даты начала."
            is NoCourseFoundException -> "Курс не найден."
            is StudentAlreadyExistsException -> "Ученик уже добавлен в курс."
            is UnknownCourseDataException -> "Ошибка в получении данных о курсе."
            else -> "Неизвестная ошибка."
        }

        val status = when (e) {
            is NoUserFoundException,
            is NoCourseFoundException -> HttpStatus.NOT_FOUND
            is UnknownCourseDataException -> HttpStatus.INTERNAL_SERVER_ERROR
            else -> HttpStatus.BAD_REQUEST
        }

        return handleExceptionInternal(e, responseBody, HttpHeaders(), status, request)
    }
}