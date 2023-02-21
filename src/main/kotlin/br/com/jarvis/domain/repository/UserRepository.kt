package br.com.jarvis.domain.repository

import br.com.jarvis.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    //    @Query(nativeQuery = true, value = "select * from \"user_entity\" where email ilike :email")
    @Query(nativeQuery = true, value = "select * from user_entity where email = :email")
    fun findByEmail(email: String?): UserEntity?

    //    @Query(nativeQuery = true, value = "select * from user where token_facebook ilike :token")
    @Query(
        "SELECT * FROM user_entity WHERE token_facebook = :token",
        nativeQuery = true
    )
    fun findByTokenFacebook(token: String): UserEntity?

    //    @Query(nativeQuery = true, value = "select count(1) > 0 from \"user_entity\" where email ilike :email")
    @Query(nativeQuery = true, value = "select count(1) > 0 from user_entity where email = :email")
    fun existsByEmail(email: String?): Boolean


    @Query(
        value = "select * from user_entity u WHERE u.name = :identity or u.email = :identity",
        nativeQuery = true
    )
    fun findByUsernameOrEmailSql(@Param("identity") username: String?): UserEntity?


    fun findByName(facebookName: String?): UserEntity?
}


