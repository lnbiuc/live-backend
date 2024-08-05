package com.lnbiuc.livebackend.`do`

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.lnbiuc.livebackend.model.dto.UserDto
import io.github.linpeilie.annotations.AutoMapper
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

@AutoMapper(target = UserDto::class, reverseConvertGenerate = false)
data class User(
    @TableId(type = IdType.ASSIGN_ID)
    var id: Long?,
    @get:JvmName("getUserUsername") // Custom getter name
    var username: String,
    @get:JvmName("getUserPassword")
    var password: String,
    var invitationCode: String,
    var disabled: Boolean,
    var createTime: LocalDate?,
    var lastLogin: LocalDate?,
    var loginCount: Int?
) : UserDetails {

    constructor(username: String, password: String) : this(
        id = null,
        username,
        password,
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
