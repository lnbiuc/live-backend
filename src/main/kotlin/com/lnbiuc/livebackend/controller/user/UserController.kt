package com.lnbiuc.livebackend.controller.user

import cn.hutool.jwt.JWT
import com.lnbiuc.livebackend.constant.SysEnum
import com.lnbiuc.livebackend.controller.user.dto.UserLoginDto
import com.lnbiuc.livebackend.controller.user.dto.UserRegisterDto
import com.lnbiuc.livebackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService, private val authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun login(@RequestBody params: UserLoginDto): ResponseEntity<String> {
        val auth = UsernamePasswordAuthenticationToken(params.username, params.password)
        authenticationManager.authenticate(auth)
        val token = JWT.create()
            .setPayload("username", params.username)
            .setKey(SysEnum.JWT_SIGN_KEY.value.toByteArray())
            .sign()

        return ResponseEntity(token, HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody params: UserRegisterDto): ResponseEntity<String> {
        userService.register(params)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/auth_required")
    fun authRequired(): ResponseEntity<String> {
        return ResponseEntity("break", HttpStatus.OK)
    }
}