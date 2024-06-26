package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.math.BigDecimal
import java.time.LocalDate

data class Oplog(
    @TableId(type = IdType.ASSIGN_ID)
    val id: Long?,
    val userId: Long?,
    val opTime: LocalDate?,
    val opCost: BigDecimal?,
    val reqParams: String?,
    val opLog: String?,
    val opType: String?,
    val opResult: String?
)
