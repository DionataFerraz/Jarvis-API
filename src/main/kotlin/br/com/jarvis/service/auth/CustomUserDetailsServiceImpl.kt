package br.com.jarvis.service.auth

import br.com.jarvis.component.security.ScopeAuthenticator
import br.com.jarvis.component.security.TokenGenerator
import br.com.jarvis.config.security.MainUser
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CustomUserDetailsServiceImpl(
    private val userRepository: UserRepository,
) : CustomUserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        println("teste ========> loadUserByUsername $username")
        val userEntity: UserEntity = userRepository.findByUsernameOrEmailSql(username)!!
        println("teste ========> CustomUserDetailsServiceImpl username $username")
        println("teste ========> CustomUserDetailsServiceImpl $userEntity")
        return MainUser().create(userEntity)
    }

    @Transactional
    override fun loadUserByUsernameOrEmail(identity: String?): MainUser? {
//        if (StringUtils.isAnyBlank(identity)) {
//            throw UsernameOrEmailNotFoundException()
//        }
        val userEntity = userRepository.findByUsernameOrEmailSql(identity)!!
        val user: MainUser = MainUser().create(userEntity)
        user.facebook = true
        return user
    }

    @Transactional
    override fun loadUserByFacebookName(facebookName: String?): MainUser? {
//        if (StringUtils.isAnyBlank(facebookName)) {
//            throw UsernameOrEmailNotFoundException()
//        }
        val userEntity = userRepository.findByName(facebookName)!!
        val user: MainUser = MainUser().create(userEntity)
        user.facebook = true
        return user
    }
}