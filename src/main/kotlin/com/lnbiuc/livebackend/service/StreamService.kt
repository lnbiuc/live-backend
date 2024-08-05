package com.lnbiuc.livebackend.service

import com.lnbiuc.livebackend.`do`.PushAddress
import org.springframework.stereotype.Service

@Service
interface StreamService {
    fun createPushAddress(): PushAddress
}