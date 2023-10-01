package br.com.jarvis.service.auth

import br.com.jarvis.domain.entity.RoleType
import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.rest.controller.auth.LoginCreateRequest
import br.com.jarvis.rest.controller.auth.LoginOutput
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class LoginServiceImpl(
    private val userRepository: UserRepository,
) : LoginService {

    @Transactional
    override fun createUser(loginCreateRequest: LoginCreateRequest): LoginOutput {
        val login = userRepository.save(
            UserEntity(
                name = loginCreateRequest.name,
                email = loginCreateRequest.email,
                password = loginCreateRequest.password,
                roleType = RoleType.valueOf(loginCreateRequest.role),
            )
        )

        return LoginOutput(
            name = login.name,
            email = login.email.orEmpty(),
            role = login.roleType.name,
        )
    }

    @Throws(Exception::class)
    @Transactional
    override fun findByEmailEntity(email: String): UserEntity? {
        return userRepository.findByEmail(email)?.orElseThrow { Exception("erro! $email") }
    }
}