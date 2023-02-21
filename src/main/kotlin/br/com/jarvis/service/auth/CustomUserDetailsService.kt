package br.com.jarvis.service.auth

import br.com.jarvis.config.security.MainUser
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {

    fun loadUserByUsernameOrEmail(identity: String?): MainUser?

    fun loadUserByFacebookName(facebookName: String?): MainUser?
}