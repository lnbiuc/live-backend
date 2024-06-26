package com.lnbiuc.livebackend.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.lnbiuc.livebackend.controller.invitation.dto.InvitationCreateDto
import com.lnbiuc.livebackend.`do`.Invitation
import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.exception.DBUpdateError
import com.lnbiuc.livebackend.exception.InvalidInvitationCode
import com.lnbiuc.livebackend.repository.InvitationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class InvitationService(private val invitationRepository: InvitationRepository) {

    fun validatorInvitationCode(invitationCode: String) {
        queryUseInvitation(invitationCode) ?: throw InvalidInvitationCode()
    }

    fun useCode(userId: Long, invitationCode: String) {
        val exist = queryUseInvitation(invitationCode)
        if (exist != null) {
            exist.usedUserId = userId
            val updateCount = invitationRepository.updateById(exist)
            if (updateCount < 1) {
                throw DBUpdateError()
            }
        }
    }

    fun queryUseInvitation(invitationCode:String): Invitation? {
        val queryWrapper = KtQueryWrapper(Invitation::class.java)
        queryWrapper.eq(Invitation::code, invitationCode)
            .ge(Invitation::expireTime, LocalDateTime.now())
            .isNull(Invitation::usedUserId)
        return invitationRepository.selectOne(queryWrapper)
    }

    fun createInvitation(dto: InvitationCreateDto) {

        fun getCodeExistRecord(code: String): List<Invitation>? {
            val queryWrapper = KtQueryWrapper(Invitation::class.java).eq(Invitation::code, code)
            return invitationRepository.selectList(queryWrapper)
        }

        val exist = getCodeExistRecord(dto.code)
        if (exist.isNullOrEmpty()) {
            throw BIZException("Repeat invitation code")
        }

        val root = 1803348194139148290L
        val invitation = Invitation(root, dto.code, dto.remark)

        val insert = invitationRepository.insert(invitation)
        if (insert < 1) {
            throw DBUpdateError()
        }
    }
}