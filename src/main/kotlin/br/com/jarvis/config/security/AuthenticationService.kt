package br.com.jarvis.config.security

import br.com.jarvis.service.auth.LoginService
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
open class AuthenticationService(private val loginService: LoginService) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return try {
            loginService.findByEmailEntity(username)!!
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}