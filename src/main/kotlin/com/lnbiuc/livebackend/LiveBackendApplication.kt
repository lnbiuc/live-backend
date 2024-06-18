package com.lnbiuc.livebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LiveBackendApplication

fun main(args: Array<String>) {
    runApplication<LiveBackendApplication>(*args)
}
