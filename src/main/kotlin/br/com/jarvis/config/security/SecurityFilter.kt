package br.com.jarvis.config.security

import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.service.auth.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(private val userRepository: UserRepository, private val tokenService: TokenService) :
    OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        retrieveToken(request)?.let { token ->
            val emailClaim: String = tokenService.validateToken(token)
            val user = userRepository.findByEmail(emailClaim) ?: throw RuntimeException("¯\\_(ツ)_/¯")

            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)

            SecurityContextHolder.getContext().authentication = authentication;
        }
        filterChain.doFilter(request, response)
    }

    private fun retrieveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
}