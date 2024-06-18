package com.lnbiuc.livebackend.controller

import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.model.dto.UserRegisterDto
import com.lnbiuc.livebackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody user: UserRegisterDto):ResponseEntity<Unit> {
        try {
            userService.registerByInvitationCode(user)
        } catch (ex: BIZException) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(HttpStatus.OK)
    }
}