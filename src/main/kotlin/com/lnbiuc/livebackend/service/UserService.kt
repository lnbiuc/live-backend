package com.lnbiuc.livebackend.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.lnbiuc.livebackend.`do`.User
import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.exception.DBUpdateError
import com.lnbiuc.livebackend.model.dto.UserRegisterDto
import com.lnbiuc.livebackend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository, private val invitationService: InvitationService) {

    @Transactional
    fun registerByInvitationCode(user: UserRegisterDto) {
        val createUser = User(user.username, user.password, user.invitationCode)
        validatorUsername(user.username)
        invitationService.validatorInvitationCode(user.invitationCode)
        val insert = userRepository.insert(createUser)
        createUser.id?.let { invitationService.useCode(it, createUser.invitationCode) }
        if (insert < 1) {
            throw DBUpdateError()
        }
    }

    private fun validatorUsername(username: String) {
        val queryWrapper = KtQueryWrapper(User::class.java)
        queryWrapper.eq(User::username, username)
            .eq(User::disabled, false)
        val list = userRepository.selectList(queryWrapper)
        if (list.isNotEmpty()) {
            throw BIZException("User name already taken")
        }
    }
}