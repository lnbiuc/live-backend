package com.lnbiuc.livebackend.controller

import com.lnbiuc.livebackend.exception.BIZException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)


    @ExceptionHandler(Exception::class)
    fun exception(ex: Exception): ResponseEntity<String> {
        logger.error(ex.message, ex)
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(BIZException::class)
    fun bizExceptionHandle(ex: BIZException): ResponseEntity<String> {
        logger.error(ex.message, ex)
        return ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, List<String?>>> {
        val errors = ex.bindingResult.fieldErrors.map { it.defaultMessage }
        return ResponseEntity.badRequest().body(mapOf("Parameter validation error" to errors))
    }
}