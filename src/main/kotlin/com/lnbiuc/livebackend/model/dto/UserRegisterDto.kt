package com.lnbiuc.livebackend.model.dto

import java.time.LocalDate

data class UserRegisterDto(
    val username: String,
    val password: String,
    val invitationCode: String,
)