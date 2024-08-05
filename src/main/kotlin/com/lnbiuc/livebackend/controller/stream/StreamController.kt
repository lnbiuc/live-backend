package com.lnbiuc.livebackend.controller.stream

import com.lnbiuc.livebackend.`do`.PushAddress
import com.lnbiuc.livebackend.service.StreamService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stream")
class StreamController(private val streamService: StreamService) {

    @PostMapping("generate_push_address")
    fun generatePushAddress(): ResponseEntity<PushAddress> {
        return ResponseEntity.ok(streamService.createPushAddress())
    }
}