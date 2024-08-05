package com.lnbiuc.livebackend.model.dto

import com.lnbiuc.livebackend.`do`.User
import io.github.linpeilie.annotations.AutoMapper
import java.time.LocalDate

@AutoMapper(target = User::class, reverseConvertGenerate = false)
data class UserDto(
    val id: Long,
    var username: String,
    var invitationCode: String,
    var disabled: Boolean,
    var createTime: LocalDate?,
    var lastLogin: LocalDate?,
    var loginCount: Int?
)