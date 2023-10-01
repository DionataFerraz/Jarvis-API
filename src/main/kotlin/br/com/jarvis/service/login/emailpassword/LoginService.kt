package br.com.jarvis.service.login.emailpassword

import br.com.jarvis.rest.controller.login.LoginCreateRequest
import br.com.jarvis.rest.controller.login.LoginOutput

interface LoginService {
    fun createUser(loginCreateRequest: LoginCreateRequest): LoginOutput
}
