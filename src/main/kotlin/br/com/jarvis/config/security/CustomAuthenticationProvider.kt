package br.com.jarvis.config.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationProvider(
    private val userDetailsService: CustomUserDetailsService
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val username = if (authentication.principal == null) "NONE_PROVIDED" else authentication.name

        println("teste ========> CustomAuthenticationProvider name $username")

        if (username.isNullOrBlank()) {
            throw BadCredentialsException("Invalid login details CustomAuthenticationProvider")
        }
        // get user details using Spring security user details service
        var user: UserDetails? = null
        user = try {
            val userEntity = userDetailsService.loadUserByUsername(username)
            println("teste ========> CustomAuthenticationProvider userEntity authorities ${userEntity.authorities}")
            println("teste ========> CustomAuthenticationProvider userEntity username ${userEntity.username}")
            println("teste ========> CustomAuthenticationProvider userEntity password ${userEntity.password}")
            userEntity
        } catch (exception: UsernameNotFoundException) {
            throw BadCredentialsException("invalid login details")
        }
        return createSuccessfulAuthentication(authentication, user)
    }

    private fun createSuccessfulAuthentication(authentication: Authentication, user: UserDetails?): Authentication? {
//        O "DEFAULT" já foi "USER"
        val token = UsernamePasswordAuthenticationToken(user!!.username, "N/A",  listOf(
            SimpleGrantedAuthority("DEFAULT")
        ))
        token.details = authentication.details//eu comentei isso e consegui resgatar o token

        println("teste ========> createSuccessfulAuthentication $token")
        return token
    }

    override fun supports(authentication: Class<*>): Boolean {
        // o Problema não é aqui, coloquei como true e para fazer o login ele da ok, mas se eu colocar pra fazer o post da ruim
        // e se eu colocar como false até o post para criar um usuário da 403
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}