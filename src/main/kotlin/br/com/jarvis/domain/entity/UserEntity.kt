package br.com.jarvis.domain.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "user_entity")
class UserEntity {
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
    var password: String? = null

    @Column(name = "birthday")
    var birthday: Date? = null

    @Column(name = "image_path", length = 1000)
    var imagePath: String? = null

    @Column(name = "token_facebook", length = 500, unique = true)
    var tokenFacebook: String? = null

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
        tokenGoogle: String? = null,
        tokenApple: String? = null,
    ) {
        this.name = name
        this.email = email
        this.password = password
        this.nickName = nickName
        this.birthday = birthday
        this.imagePath = imagePath
        this.tokenFacebook = tokenFacebook
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
}
