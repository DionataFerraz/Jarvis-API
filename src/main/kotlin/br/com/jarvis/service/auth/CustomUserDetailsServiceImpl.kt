package br.com.jarvis.service.auth

import br.com.jarvis.config.security.UserDetailsImpl
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
        println("teste ========> CustomUserDetailsServiceImpl $userEntity")


        return UserDetailsImpl(userEntity)
    }


    @Transactional
    override fun loadUserByUsernameOrEmail(identity: String?): UserDetailsImpl? {
        val userEntity = userRepository.findByUsernameOrEmailSql(identity)
            ?: throw UsernameNotFoundException("Error loadUserByUsernameOrEmail Service $identity")
        val user = UserDetailsImpl(userEntity)
        user.facebook = true
        return user
    }


    @Transactional
    override fun loadUserByFacebookName(facebookName: String?): UserDetailsImpl? {
        val userEntity = userRepository.findByName(facebookName)
            ?: throw UsernameNotFoundException("Error loadUserByFacebookName Service $facebookName")
        val user = UserDetailsImpl(userEntity)
        user.facebook = true
        return user
    }
}