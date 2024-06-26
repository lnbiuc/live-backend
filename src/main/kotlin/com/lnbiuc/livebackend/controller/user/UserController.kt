package com.lnbiuc.livebackend.controller.user

import com.lnbiuc.livebackend.exception.BIZException
import com.lnbiuc.livebackend.exception.DBUpdateError
import com.lnbiuc.livebackend.exception.InvalidInvitationCode
import com.lnbiuc.livebackend.controller.user.dto.UserRegisterDto
import com.lnbiuc.livebackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody user: UserRegisterDto): ResponseEntity<String> {
        try {
            userService.registerByInvitationCode(user)
        } catch (biz: BIZException) {
            return ResponseEntity(biz.message, HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (db: DBUpdateError) {
            return ResponseEntity(db.message, HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (code: InvalidInvitationCode) {
            return ResponseEntity(code.message, HttpStatus.FORBIDDEN)
        } catch (e: Throwable) {
            return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return ResponseEntity(HttpStatus.OK)
    }
}