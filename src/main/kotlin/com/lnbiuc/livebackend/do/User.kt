package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

data class User(
    @TableId(type = IdType.ASSIGN_ID)
    val id: Long?,
    @get:JvmName("getUserUsername") // Custom getter name
    val username: String,
    @get:JvmName("getUserPassword")
    val password: String,
    val pwdSale: String?,
    val invitationCode: String,
    val disabled: Boolean,
    val createTime: LocalDate?,
    val lastLogin: LocalDate?,
    val loginCount: Int?
) : UserDetails {

    constructor(username: String, password: String) : this(
        id = null,
        username,
        password,
        pwdSale = null,
        invitationCode = "default",
        disabled = false,
        createTime = LocalDate.now(),
        lastLogin = LocalDate.now(),
        loginCount = 0
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val role = HashSet<GrantedAuthority>()
        role.add(SimpleGrantedAuthority("ROLE_USER"))
        role.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        return role
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }
}
