package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.time.LocalDate

data class Invitation(

    @TableId(type = IdType.ASSIGN_ID)
    var id: Long?,
    var usedUserId: Long?,
    var creator: Long?,
    var createTime: LocalDate?,
    var expireTime: LocalDate?,
    var code: String?,
    var remark: String?
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
