package com.lnbiuc.livebackend.service

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.lnbiuc.livebackend.`do`.Invitation
import com.lnbiuc.livebackend.exception.InvalidInvitationCode
import com.lnbiuc.livebackend.repository.InvitationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InvitationService(private val invitationRepository: InvitationRepository) {

    fun validatorInvitationCode(invitationCode: String) {
        val queryWrapper = LambdaQueryWrapper<Invitation>()
        queryWrapper.eq(Invitation::code, invitationCode)
            .lt(Invitation::expireTime, LocalDateTime.now())
            .isNull(Invitation::usedUserId)
        val exist = invitationRepository.selectOne(queryWrapper)
        if (exist != null) {
            throw InvalidInvitationCode()
        }
    }

    fun useCode(id: Long, invitationCode: String) {
        invitationRepository.updateByCode(invitationCode, id)
    }
}