package br.com.jarvis.domain.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "user_entity")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    val id: Long? = null,
    @Column(name = "name", length = 500, unique = true, nullable = false)
    val name: String,
    @Column(name = "nick_name", length = 500)
    var nickName: String? = null,
    @Column(name = "email", length = 500)
    val email: String,
    @Column(name = "password", length = 500)
    val pass: String? = null,
    @Column(name = "birthday")
    val birthday: Date? = null,
    @Column(name = "image_path", length = 1000)
    val imagePath: String? = null,
    @Column(name = "role_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val roleType: RoleType,
    @Column(name = "token_facebook", length = 500, unique = true)
    var tokenFacebook: String? = null,
) : UserDetails {

//    @Column(name = "token_facebook", length = 500, unique = true)
//    var tokenFacebook: String? = null

//    @Column(name = "refresh_token_facebook", length = 500, unique = true)
//    var refreshTokenFacebook: UUID? = null

//    @Column(name = "token_google", length = 500, unique = true)
//    var tokenGoogle: String? = null

//    @Column(name = "refresh_token_google", length = 500, unique = true)
//    var refreshTokenGoogle: UUID? = null

//    @Column(name = "token_apple", length = 500, unique = true)
//    var tokenApple: String? = null

//    @Column(name = "refresh_token_apple", length = 500, unique = true)
//    var refreshTokenApple: UUID? = null

    override fun toString(): String {
        return "UserEntity(" +
                "   id = $id," +
                "   name = $name," +
                "   email = $email," +
                "   password = $pass," +
                "   nickName = $nickName," +
                "   birthday = $birthday," +
                "   imagePath = $imagePath," +
                "   tokenFacebook = $tokenFacebook," +
                ")"
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return setOf(SimpleGrantedAuthority(roleType.name))
    }

    override fun getPassword(): String {
        return this.pass.orEmpty()
    }

    override fun getUsername(): String {
        return this.email
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
}
