package com.lnbiuc.livebackend.service.impl

import com.lnbiuc.livebackend.`do`.User
import com.lnbiuc.livebackend.service.UserService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(private val userService: UserService): UserDetailsService {

    override fun loadUserByUsername(username: String?): User? {
        return userService.getUserByUsername(username)
    }
}