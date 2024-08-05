package com.lnbiuc.livebackend.controller.user.dto

import jakarta.validation.constraints.NotBlank


data class UserRegisterDto(
    @field:NotBlank(message = "username cannot be blank")
    val username: String?,
    @field:NotBlank(message = "password cannot be blank")
    val password: String?,
    @field:NotBlank(message = "code cannot be blank")
    val code: String?
)