package com.lnbiuc.livebackend.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.lnbiuc.livebackend.controller.invitation.dto.InvitationCreateDto
import com.lnbiuc.livebackend.`do`.Invitation
import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.repository.InvitationRepository
import com.lnbiuc.livebackend.service.InvitationService
import java.time.LocalDateTime

class InvitationServiceImpl(private val invitationRepository: InvitationRepository): InvitationService {
    override fun createInvitation(dto: InvitationCreateDto) {
        val code = dto.code
        val queryWrapper = KtQueryWrapper(Invitation::class.java)
        queryWrapper.eq(Invitation::code, code).ge(Invitation::expireTime, LocalDateTime.now())
        val exist = invitationRepository.selectList(queryWrapper)
        if (exist.isEmpty()) {
            val invitation = Invitation(null, code, dto.remark)
            invitationRepository.insert(invitation)
        } else {
            throw BIZException("Invitation code already used")
        }
    }
}