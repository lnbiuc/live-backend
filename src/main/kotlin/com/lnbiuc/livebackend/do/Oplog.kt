package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.math.BigDecimal
import java.time.LocalDate

data class Oplog(
    @TableId(type = IdType.ASSIGN_ID)
    var id: Long?,
    var userId: Long?,
    var opTime: LocalDate?,
    var opCost: BigDecimal?,
    var reqParams: String?,
    var opLog: String?,
    var opType: String?,
    var opResult: String?
)
