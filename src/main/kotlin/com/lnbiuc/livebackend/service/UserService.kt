package com.lnbiuc.livebackend.service

import com.lnbiuc.livebackend.`do`.User
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
        invitationService.validatorInvitationCode(user.invitationCode)
        val insert = userRepository.insert(createUser)
        invitationService.useCode(createUser.id, createUser.invitationCode)
        if (insert < 1) {
            throw DBUpdateError()
        }
    }
}