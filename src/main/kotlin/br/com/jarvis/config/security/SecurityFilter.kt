package br.com.jarvis.config.security

import br.com.jarvis.service.auth.LoginService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

//@Component
//open class SecurityFilter(
//    private val tokenService: TokenService,
//    private val loginService: LoginService,
//) : OncePerRequestFilter() {
//
//    @Throws(ServletException::class, IOException::class)
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val token = recoverToken(request)
//        if (token != null) {
//            val login = tokenService.validateToken(token)
//            var user: UserDetails? = null
//            try {
//                user = loginService.findByLoginEntity(login)
//                val authentication = UsernamePasswordAuthenticationToken(user, null, user?.authorities)
//                SecurityContextHolder.getContext().authentication = authentication
//            } catch (e: Exception) {
//                throw RuntimeException(e)
//            }
//        }
//        filterChain.doFilter(request, response)
//    }
//
//    private fun recoverToken(request: HttpServletRequest): String? {
//        val authHeader = request.getHeader("Authorization") ?: return null
//        return authHeader.replace("Bearer ", "")
//    }
//}