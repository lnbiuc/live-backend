package com.lnbiuc.livebackend.controller.invitation

import com.lnbiuc.livebackend.controller.invitation.dto.InvitationCreateDto
import com.lnbiuc.livebackend.service.InvitationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/invitation")
class InvitationController(private val invitationService: InvitationService) {

    @PostMapping("/create")
    fun create(@RequestBody invitationCreateDto :InvitationCreateDto): ResponseEntity<String> {
        try {
            invitationService.createInvitation(invitationCreateDto)
        } catch (ex: Exception) {
            return ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(HttpStatus.CREATED)
    }
}