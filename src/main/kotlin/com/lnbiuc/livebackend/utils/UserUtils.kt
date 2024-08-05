package com.lnbiuc.livebackend.utils

import com.lnbiuc.livebackend.`do`.User
import com.lnbiuc.livebackend.model.dto.UserDto
import org.springframework.security.core.context.SecurityContextHolder


object UserUtils {

    fun getCurrentUser(): UserDto {
        val (id, username, password, invitationCode, disabled, createTime, lastLogin, loginCount) =
            SecurityContextHolder.getContext().authentication.principal as User
        return UserDto(id!!, username, invitationCode, disabled, createTime, lastLogin, loginCount)
    }
}