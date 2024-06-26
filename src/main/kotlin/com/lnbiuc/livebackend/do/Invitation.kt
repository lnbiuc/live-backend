package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.time.LocalDate

data class Invitation(

    @TableId(type = IdType.ASSIGN_ID)
    val id: Long?,
    var usedUserId: Long?,
    val creator: Long?,
    val createTime: LocalDate?,
    val expireTime: LocalDate?,
    val code: String?,
    val remark: String?
)
