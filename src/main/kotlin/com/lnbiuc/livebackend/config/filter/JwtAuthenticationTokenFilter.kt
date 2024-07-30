package com.lnbiuc.livebackend.config.filter

import com.lnbiuc.livebackend.constant.SysEnum
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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

        val authHeader = request.getHeader(SysEnum.AUTH_HEADER.value)
//
//        val userDetails: UserDetails = userDetailsService.loadUserByUsername("")
//        val authentication =
//            UsernamePasswordAuthenticationToken(
//                userDetails.username,
//                userDetails.password,
//                userDetails.authorities
//            )
//        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        filterChain.doFilter(request, response)
    }
}