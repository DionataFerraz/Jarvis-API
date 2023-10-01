package br.com.jarvis.service.login.emailpassword

import br.com.jarvis.domain.entity.UserEntity
import br.com.jarvis.domain.repository.UserRepository
import br.com.jarvis.rest.controller.login.LoginCreateRequest
import br.com.jarvis.rest.controller.login.LoginOutput
import br.com.jarvis.service.login.RoleType
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
}