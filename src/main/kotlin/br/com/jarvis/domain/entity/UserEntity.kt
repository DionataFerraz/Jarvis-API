package br.com.jarvis.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "user_entity")
class UserEntity: UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long? = null

    @Column(name = "name", length = 500, unique = true, nullable = false)
    @NotEmpty(message = "User name field is required.")
    lateinit var name: String

    @Column(name = "nick_name", length = 500)
    var nickName: String? = null

    @Column(name = "email", length = 500)
    var email: String? = null

    @Column(name = "password", length = 500)
    var pwd: String? = null

    @Column(name = "birthday")
    var birthday: Date? = null

    @Column(name = "image_path", length = 1000)
    var imagePath: String? = null

    @Column(name = "token_facebook", length = 500, unique = true)
    var tokenFacebook: String? = null

    @Column(name = "role_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role type field is required.")
    lateinit var roleType: RoleType

//    @Column(name = "refresh_token_facebook", length = 500, unique = true)
//    var refreshTokenFacebook: UUID? = null

    @Column(name = "token_google", length = 500, unique = true)
    var tokenGoogle: String? = null

//    @Column(name = "refresh_token_google", length = 500, unique = true)
//    var refreshTokenGoogle: UUID? = null

    @Column(name = "token_apple", length = 500, unique = true)
    var tokenApple: String? = null

//    @Column(name = "refresh_token_apple", length = 500, unique = true)
//    var refreshTokenApple: UUID? = null

    constructor() {}

    constructor(
        name: String,
        email: String? = null,
        password: String? = null,
        nickName: String? = null,
        birthday: Date? = null,
        imagePath: String? = null,
        tokenFacebook: String? = null,
        roleType: RoleType,
        tokenGoogle: String? = null,
        tokenApple: String? = null,
    ) {
        this.name = name
        this.email = email
        this.pwd = password
        this.nickName = nickName
        this.birthday = birthday
        this.imagePath = imagePath
        this.tokenFacebook = tokenFacebook
        this.roleType = roleType
        this.tokenGoogle = tokenGoogle
        this.tokenApple = tokenApple
    }

    override fun toString(): String {
        return "UserEntity(" +
                "   id = $id," +
                "   name = $name," +
                "   email = $email," +
                "   password = $password," +
                "   nickName = $nickName," +
                "   birthday = $birthday," +
                "   imagePath = $imagePath," +
                ")"
    }


    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return setOf(SimpleGrantedAuthority(roleType.name))
    }

    override fun getPassword(): String {
        return pwd.orEmpty()
    }

    override fun getUsername(): String {
        return email.orEmpty()
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
