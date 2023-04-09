package br.com.jarvis.component.security

import org.springframework.security.authentication.AuthenticationManager
import br.com.jarvis.config.security.UserDetailsImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.HashMap
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
open class ScopeAuthenticator(private val authenticationManager: AuthenticationManager) {
    fun authenticate(userDetailsImpl: UserDetailsImpl?, scope: String) {
        println("teste ========> ScopeAuthenticator authenticate")
        val token = UsernamePasswordAuthenticationToken(userDetailsImpl, null, listOf(SimpleGrantedAuthority("DEFAULT")))
        val params: MutableMap<String, String> = HashMap()
        params["scope"] = scope
        token.details = params

        println("teste ========> ScopeAuthenticator token $token")
        val authentication = authenticationManager.authenticate(token)
        println("teste ========> ScopeAuthenticator authentication")
        SecurityContextHolder.getContext().authentication = authentication
        println("teste ========> SecurityContextHolder")
    }
}