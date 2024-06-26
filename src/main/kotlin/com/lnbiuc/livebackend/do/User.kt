package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.time.LocalDate

data class User(
    @TableId(type = IdType.ASSIGN_ID)
    val id: Long?,
    val username: String?,
    val password: String?,
    val pwdSale: String?,
    val invitationCode: String,
    val disabled: Boolean?,
    val createTime: LocalDate?,
    val lastLogin: LocalDate?,
    val loginCount: Int?
) {
    constructor(username: String, password: String, invitationCode: String): this(
        id = null,
        username = username,
        password = handlePassword(password)["pwd"],
        pwdSale = handlePassword(password)["sale"],
        invitationCode = invitationCode,
        disabled = false,
        createTime = LocalDate.now(),
        lastLogin = null,
        loginCount = 0
    )

    companion object {
        fun handlePassword(password: String): Map<String, String> {
            val sale = generatePwdSale(password)
            return mapOf("sale" to sale, "pwd" to password)
        }

        private fun generatePwdSale(password: String): String {
            return "temp_$password"
        }
    }
}
