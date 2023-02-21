package br.com.jarvis.config.security

import br.com.jarvis.domain.entity.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

private const val serialVersionUID = 1

//        O "DEFAULT" já foi "USER"
private const val DEFAULT_ROLE = "DEFAULT"


//data class MainUser(
//    val id: Long = 0,
//    val enabled: Boolean = false,
//    val email: String = "",
//) : UserDetails {
//
//    private lateinit var username: String
//    private lateinit var password: String
//    private var authorities: Collection<GrantedAuthority> = listOf()
//    var facebook: Boolean = false
//
//
//    override fun getUsername(): String {
//        return username
//    }
//
//    override fun getPassword(): String {
//        return password
//    }
//
//    override fun getAuthorities(): Collection<GrantedAuthority> {
//        return authorities
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return true
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isEnabled(): Boolean {
//        return enabled
//    }
//
//    fun create(userEntity: UserEntity): MainUser {
//        val authorities = listOf<GrantedAuthority>(
//            SimpleGrantedAuthority(DEFAULT_ROLE)
//        )
//        return MainUser(
//            id = userEntity.id ?: 0,
//            enabled = true,
//            email = userEntity.email ?: "email"
//        ).apply {
//            this.username = userEntity.name
//            this.password = userEntity.password ?: "password"
//            this.authorities = authorities
//        }
//    }
//}

data class MainUser(private val user: UserEntity, var facebook: Boolean = false) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        return true
    }
    override fun getUsername(): String {
        return user.email.orEmpty()
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
//teste ========> QUALQUER COISA authenticate2 UsernamePasswordAuthenticationToken [Principal=br.com.jarvis.config.security.MainUser@2e31b14f, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[DEFAULT]]
//teste ========> QUALQUER COISA authenticate2 UsernamePasswordAuthenticationToken [Principal=MainUser(id=1, enabled=true, email=email), Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[DEFAULT]]
