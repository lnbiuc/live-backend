package com.lnbiuc.livebackend.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.lnbiuc.livebackend.controller.invitation.dto.InvitationCreateDto
import com.lnbiuc.livebackend.`do`.Invitation
import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.model.dto.UserDto
import com.lnbiuc.livebackend.repository.InvitationRepository
import com.lnbiuc.livebackend.service.InvitationService
import com.lnbiuc.livebackend.utils.UserUtils
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class InvitationServiceImpl(private val invitationRepository: InvitationRepository): InvitationService {
    override fun createInvitation(dto: InvitationCreateDto) {
        val user: UserDto = UserUtils.getCurrentUser()
        val code = dto.code
        if (!exist(dto.code)) {
            val invitation = Invitation(user.id, code, dto.remark)
            invitationRepository.insert(invitation)
        } else {
            throw BIZException("exist Invitation code")
        }
    }

    fun exist(code: String): Boolean {
        val queryWrapper = KtQueryWrapper(Invitation::class.java)
        queryWrapper.eq(Invitation::code, code).ge(Invitation::expireTime, LocalDateTime.now())
        return invitationRepository.exists(queryWrapper);
    }

    override fun validatorUseAble(code: String): Boolean {
        val queryWrapper = KtQueryWrapper(Invitation::class.java)
        queryWrapper.eq(Invitation::code, code).ge(Invitation::expireTime, LocalDateTime.now())
        val list = invitationRepository.selectList(queryWrapper)
        if (list.size != 0) {
            val invitation = list[0]
            val usedUserId = invitation.usedUserId
            if (usedUserId != null) {
                throw BIZException("Code has be used")
            }
            val expireTime = invitation.expireTime
            if (expireTime!!.isBefore(LocalDate.now())) {
                throw BIZException(String.format("Code expired At: %s", expireTime))
            }
        }
        return true
    }

    override fun consumptionInvitationCode(code: String, id: Long?) {
        val queryWrapper = KtQueryWrapper(Invitation::class.java)
        queryWrapper.eq(Invitation::code, code).ge(Invitation::expireTime, LocalDateTime.now())
        val invitations = invitationRepository.selectList(queryWrapper)
        if (invitations.isEmpty()) {
            throw Exception("code has expired")
        }
        val invitation = invitations[0]
        invitation.usedUserId = id
        invitationRepository.updateById(invitation)
    }
}