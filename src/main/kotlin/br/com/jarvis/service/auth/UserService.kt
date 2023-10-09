package br.com.jarvis.service.auth

import br.com.jarvis.domain.entity.RoleType
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.exception.UserRuleException
import br.com.jarvis.rest.controller.auth.CreateUserRequest
import br.com.jarvis.rest.controller.auth.LoginOutput
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username) ?: throw UserRuleException("User not found")
    }

    fun createUser(createUserRequest: CreateUserRequest): LoginOutput {
        return userRepository.save(createUserRequest.toEntity()).toLoginOutput()
    }

    private fun CreateUserRequest.toEntity(): UserEntity {
        val encryptedPassword = passwordEncoder.encode(this.password)

        return UserEntity(
            name = this.name,
            email = this.email,
            pass = encryptedPassword,
            roleType = RoleType.valueOf(this.role),
        )
    }

    private fun UserEntity.toLoginOutput(): LoginOutput {
        return LoginOutput(
            name = this.name,
            email = this.email,
            role = this.roleType.name,
        )
    }
}