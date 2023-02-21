package br.com.jarvis.config.security

import br.com.jarvis.domain.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

private const val serialVersionUID = 2692059351417516589L
private const val DEFAULT_ROLE = "USER"

class MainUser : UserDetails {
    var id: Long = 0
    var password2: String? = "password2"
    var userName: String? = "userName"
    var name: String? = "name"
    var email: String? = "email"
    var facebook = java.lang.Boolean.FALSE
    lateinit var authorities2: Collection<GrantedAuthority>
    override fun getUsername(): String? {
        return name
    }

    override fun getPassword(): String? {
        return password2
    }

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return authorities2
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun create(userEntity: UserEntity): MainUser {
        val authorities = listOf<GrantedAuthority>(
            SimpleGrantedAuthority(DEFAULT_ROLE)
        )
        return MainUser().apply {
            id = userEntity.id ?: 0
            name = userEntity.name
            userName = userEntity.name
            facebook = false
            email = userEntity.email
            this.authorities2 = authorities
        }
    }
}