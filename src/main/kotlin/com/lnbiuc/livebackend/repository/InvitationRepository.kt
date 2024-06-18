package com.lnbiuc.livebackend.repository

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.lnbiuc.livebackend.`do`.Invitation
import org.springframework.stereotype.Repository

@Repository
interface InvitationRepository : BaseMapper<Invitation> {

    fun updateByCode(invitationCode: String, userId: Long) {
        val queryWrapper = LambdaQueryWrapper<Invitation>()
        queryWrapper.eq(Invitation::code, invitationCode)
        val exist = this.selectOne(queryWrapper)
        exist.usedUserId = userId
        this.updateById(exist)
    }
}