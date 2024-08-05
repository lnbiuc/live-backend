package com.lnbiuc.livebackend.`do`

import java.time.LocalDateTime

data class PushAddress(
    var id: Long?,
    var name: String,
    var address: String,
    var code: String,
    var creator: Long = 0,
    var createTime: LocalDateTime = LocalDateTime.now(),
)
