package com.lnbiuc.livebackend.service

import com.lnbiuc.livebackend.controller.invitation.dto.InvitationCreateDto
import org.springframework.stereotype.Service

@Service
interface InvitationService {
    fun createInvitation(invitationCreateDto: InvitationCreateDto)
    fun validatorUseAble(code: String): Boolean
    fun consumptionInvitationCode(code: String, id: Long?)
}