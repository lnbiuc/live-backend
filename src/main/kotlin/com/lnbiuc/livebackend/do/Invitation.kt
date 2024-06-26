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
) {
    constructor(creator: Long?, code: String?, remark: String?) : this(
        id = null,
        usedUserId = null,
        creator = creator,
        createTime = LocalDate.now(),
        expireTime = LocalDate.now().plusDays(30),
        code = code,
        remark = remark
    )
}
