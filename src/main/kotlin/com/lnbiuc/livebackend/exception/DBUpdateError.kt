package com.lnbiuc.livebackend.exception

class DBUpdateError : Exception {
    constructor(message: String) : super(message)
    constructor() : super("Database update failed")
}