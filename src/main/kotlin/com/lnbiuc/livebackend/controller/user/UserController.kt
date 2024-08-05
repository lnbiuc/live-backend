package com.lnbiuc.livebackend.controller.user

import cn.hutool.jwt.JWT
import com.lnbiuc.livebackend.constant.SysEnum
import com.lnbiuc.livebackend.controller.user.dto.UserLoginDto
import com.lnbiuc.livebackend.controller.user.dto.UserRegisterDto
import com.lnbiuc.livebackend.service.impl.UserDetailServiceImpl
import com.lnbiuc.livebackend.utils.UserUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserController(private val userDetailService: UserDetailServiceImpl, private val authenticationManager: AuthenticationManager) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)


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
    fun register(@Validated @RequestBody params: UserRegisterDto): ResponseEntity<String> {
        userDetailService.register(params)
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("auth_required")
    fun authRequired(): ResponseEntity<String> {
        val currentUser = UserUtils.getCurrentUser()
        return ResponseEntity(currentUser.toString(), HttpStatus.OK)
    }
}