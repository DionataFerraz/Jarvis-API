package br.com.jarvis.service.auth

import br.com.jarvis.config.security.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {
    fun loadUserByUsernameOrEmail(identity: String?): UserDetailsImpl?
    fun loadUserByFacebookName(facebookName: String?): UserDetailsImpl?
}