package br.com.jarvis.config.security

import br.com.jarvis.domain.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority

data class UserDetailsImpl(private val user: UserEntity, var facebook: Boolean = false) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        return true
    }
    override fun getUsername(): String {
        return user.name
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return user.password.orEmpty()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
