package br.com.jarvis.component.security

import br.com.jarvis.service.auth.CustomUserDetailsService
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

   /* @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val name: String = authentication.getName()
//        val password: String = authentication.getCredentials()



        println("teste ========> CustomAuthenticationProvider name $name")
        println("teste ========> CustomAuthenticationProvider credentials ${authentication.credentials}")
        println("teste ========> CustomAuthenticationProvider authorities ${authentication.authorities}")
        println("teste ========> CustomAuthenticationProvider principal ${authentication.principal}")
//        println("teste ========> CustomAuthenticationProvider $password")


        val loadedUser = userDetailsService.loadUserByUsername(name)
//        println("teste ========> retrieveUser getPrincipal ${authentication.getPrincipal()}")
//        println("teste ========> retrieveUser loadedUser ${loadedUser}")

//        if (loadedUser?.username == null && !loadedUser?.facebook) {
//            throw ComicBookExistsException
//        }
        if (loadedUser == null) {
            throw ComicBookExistsException
        }

//        val grantedAuths: MutableList<GrantedAuthority> = java.util.ArrayList()
//        grantedAuths.add(SimpleGrantedAuthority("USER"))

//        val principal: UserDetails = User(loadedUser.username, loadedUser.password, grantedAuths)
        return UsernamePasswordAuthenticationToken(
            authentication.principal,
            authentication.credentials,
            authentication.authorities
        )

//        return UsernamePasswordAuthenticationToken(
//            name, "password2", ArrayList()
//        )
        *//* return if (shouldAuthenticateAgainstThirdPartySystem()) {

             // use the credentials
             // and authenticate against the third-party system
             UsernamePasswordAuthenticationToken(
                 name, password, ArrayList()
             )
         } else {
             null
         }*//*
    }*/


    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val username = if (authentication.principal == null) "NONE_PROVIDED" else authentication.name

        println("teste ========> CustomAuthenticationProvider name $username")

        if (username.isNullOrBlank()) {
            throw BadCredentialsException("invalid login details")
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
        token.details = authentication.details

        println("teste ========> createSuccessfulAuthentication $token")
        return token
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}