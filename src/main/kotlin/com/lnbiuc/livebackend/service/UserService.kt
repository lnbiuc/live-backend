package com.lnbiuc.livebackend.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.lnbiuc.livebackend.controller.user.dto.UserRegisterDto
import com.lnbiuc.livebackend.`do`.User
import com.lnbiuc.livebackend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun getUserByUsername(username: String?): User? {
        val queryWrapper = KtQueryWrapper(User::class.java)
        queryWrapper.eq(User::username, username)
            .eq(User::disabled, false)
        return userRepository.selectOne(queryWrapper)
    }

    fun register(params: UserRegisterDto) {
        val password = passwordEncoder.encode(params.password)
        val user = User(params.username, password)
        userRepository.insert(user)
    }
}