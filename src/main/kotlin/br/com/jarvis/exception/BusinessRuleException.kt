package br.com.jarvis.exception

class BusinessRuleException(override val message: String) : RuntimeException(message)