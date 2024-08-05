package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.time.LocalDate

data class Room(
    @TableId(type = IdType.ASSIGN_ID)
    var id: Long?,
    var userId: Long?,
    var createTime: LocalDate?,
    var expireTime: LocalDate?,
    var recordId: Long?,
    var pull: String?,
    var title: String?,
    var desc: String?,
    var push: String?
)
