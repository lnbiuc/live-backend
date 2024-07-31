package com.lnbiuc.livebackend.config.filter

import cn.hutool.jwt.JWTUtil
import com.lnbiuc.livebackend.constant.SysEnum
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationTokenFilter(private val userDetailsService: UserDetailsService): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = request.getHeader(SysEnum.AUTH_HEADER.value)
        if (token != null) {
            val jwt = JWTUtil.parseToken(token)
            val username = jwt.getPayload("username")
            val userDetails = userDetailsService.loadUserByUsername(username.toString())
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}