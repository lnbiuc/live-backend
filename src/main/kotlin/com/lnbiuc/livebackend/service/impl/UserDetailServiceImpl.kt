package com.lnbiuc.livebackend.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.lnbiuc.livebackend.controller.user.dto.UserRegisterDto
import com.lnbiuc.livebackend.`do`.User
import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.repository.UserRepository
import com.lnbiuc.livebackend.service.InvitationService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val invitationService: InvitationService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): User? {
        val queryWrapper = KtQueryWrapper(User::class.java)
        queryWrapper.eq(User::username, username)
            .eq(User::disabled, false)
        return userRepository.selectOne(queryWrapper)
    }

    fun register(params: UserRegisterDto) {
        val exist = loadUserByUsername(params.username)
        if (exist != null) {
            throw BIZException("User already exists")
        }
        val codeUseAble = invitationService.validatorUseAble(params.code!!)
        if (!codeUseAble) {
            throw BIZException("Invalid code")
        }
        val password = passwordEncoder.encode(params.password)
        val user = User(params.username!!, password)
        user.invitationCode = params.code
        userRepository.insert(user)

        invitationService.consumptionInvitationCode(params.code, user.id)
    }
}