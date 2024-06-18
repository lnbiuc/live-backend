package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.time.LocalDate

data class Room(
    @TableId(type = IdType.ASSIGN_ID)
    val id: Long,
    val userId: Long?,
    val createTime: LocalDate?,
    val expireTime: LocalDate?,
    val recordId: Long?,
    val pull: String?,
    val title: String?,
    val desc: String?,
    val push: String?
)
