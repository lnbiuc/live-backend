package com.lnbiuc.livebackend.controller.user.dto

data class UserRegisterDto(
    val username: String,
    val password: String,
    val invitationCode: String,
)