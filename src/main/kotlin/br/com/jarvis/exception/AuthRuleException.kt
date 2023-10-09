package br.com.jarvis.exception

class AuthRuleException(override val message: String) : RuntimeException(message)

class UserRuleException(override val message: String) : RuntimeException(message)