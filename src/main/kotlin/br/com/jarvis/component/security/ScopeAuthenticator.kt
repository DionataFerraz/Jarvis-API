package br.com.jarvis.component.security

import org.springframework.security.authentication.AuthenticationManager
import br.com.jarvis.config.security.MainUser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.HashMap
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

// Para funcionar eu preciso adicioanar uma config no SecurityConfig
@Service
open class ScopeAuthenticator(private val authenticationManager: AuthenticationManager) {
    fun authenticate(mainUser: MainUser?, scope: String) {
        println("teste ========> QUALQUER COISA authenticate")
        val token = UsernamePasswordAuthenticationToken(mainUser, null, listOf(SimpleGrantedAuthority("DEFAULT")))
        val params: MutableMap<String, String> = HashMap()
//        params["scope"] = scope
//        token.details = params
        println("teste ========> QUALQUER COISA authenticate2 $token")
        val authentication = authenticationManager.authenticate(token)
        println("teste ========> QUALQUER COISA authenticate3")
        SecurityContextHolder.getContext().authentication = authentication
        println("teste ========> QUALQUER COISA authenticate4")
    }
}