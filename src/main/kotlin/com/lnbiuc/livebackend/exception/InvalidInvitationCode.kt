package com.lnbiuc.livebackend.exception

class InvalidInvitationCode : Exception {
    constructor(message: String) : super(message)
    constructor() : super("Invalid invitation code")
}